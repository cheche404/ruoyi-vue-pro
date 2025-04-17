package cn.iocoder.yudao.module.system.controller.admin.oidc;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.http.HttpUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2ClientService;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2GrantService;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.util.oauth2.OAuth2Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * OpenID Connect (OIDC) 认证控制器
 *
 * 提供符合 OIDC 规范的接口，实现单点登录(SSO)
 *
 * @author 您的名字
 */
@Tag(name = "管理后台 - OpenID Connect 认证")
@RestController
@RequestMapping("/system/oidc")
@Validated
@Slf4j
public class OIDCServerController {

  @Resource
  private OAuth2GrantService oauth2GrantService;
  @Resource
  private OAuth2ClientService oauth2ClientService;
  @Resource
  private OAuth2TokenService oauth2TokenService;
  @Resource
  private AdminUserService adminUserService;
  @Resource
  private StringRedisTemplate stringRedisTemplate;

  private static final String ARCHERY_SSO_PREFIX = "archery_sso_";

  @Value("${sso.oidc.private-key}")
  private String privateKeyPem;

  private PrivateKey privateKey;
  private PublicKey publicKey;

  private static final String OIDC_SESSION_PREFIX = "oidc_session_";
  private static final String OIDC_NONCE_PREFIX = "oidc_nonce_";
  private static final int NONCE_EXPIRE_MINUTES = 10;

  @PostConstruct
  public void init() {
    try {
      this.privateKey = loadPrivateKey(privateKeyPem);
      this.publicKey = generatePublicKeyFromPrivateKey(privateKey);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to initialize RSA keys", e);
    }
  }

  @GetMapping("/.well-known/openid-configuration")
  @PermitAll
  @Operation(summary = "OIDC 配置发现端点")
  public Map<String, Object> discoveryConfiguration(HttpServletRequest request) {
    String issuer = getIssuerUrl(request);
    Map<String, Object> discovery = new HashMap<>();
    discovery.put("issuer", issuer);
    discovery.put("authorization_endpoint", issuer + "/admin-api/system/oidc/authorize");
    discovery.put("token_endpoint", issuer + "/admin-api/system/oidc/token");
    discovery.put("userinfo_endpoint", issuer + "/admin-api/system/oidc/userinfo");
    discovery.put("jwks_uri", issuer + "/admin-api/system/oidc/jwks");
    discovery.put("end_session_endpoint", issuer + "/admin-api/system/auth/logout");

    discovery.put("response_types_supported", Arrays.asList("code", "token", "id_token", "code id_token", "code token", "token id_token", "code token id_token"));
    discovery.put("subject_types_supported", Collections.singletonList("public"));
    discovery.put("id_token_signing_alg_values_supported", Collections.singletonList("RS256")); // 修改为仅支持 RS256
    discovery.put("scopes_supported", Arrays.asList("openid", "profile", "email", "address", "phone"));
    discovery.put("token_endpoint_auth_methods_supported", Arrays.asList("client_secret_basic", "client_secret_post"));
    discovery.put("claims_supported", Arrays.asList("sub", "iss", "name", "family_name", "given_name", "preferred_username", "email"));

    return discovery;
  }

  @GetMapping("/authorize")
  @Operation(summary = "OIDC 授权端点")
  @Parameters({
    @Parameter(name = "response_type", required = true, description = "响应类型", example = "code"),
    @Parameter(name = "client_id", required = true, description = "客户端编号", example = "client1"),
    @Parameter(name = "redirect_uri", required = true, description = "重定向URI", example = "https://example.com/callback"),
    @Parameter(name = "scope", description = "授权范围", example = "openid profile email"),
    @Parameter(name = "state", description = "状态", example = "xyz"),
    @Parameter(name = "nonce", description = "随机数", example = "n-0S6_WzA2Mj")
  })
  public ModelAndView authorize(
    @RequestParam("response_type") String responseType,
    @RequestParam("client_id") String clientId,
    @RequestParam("redirect_uri") String redirectUri,
    @RequestParam(value = "scope", required = false, defaultValue = "openid") String scope,
    @RequestParam(value = "state", required = false) String state,
    @RequestParam(value = "nonce", required = false) String nonce,
    HttpServletRequest request
  ) {

    try {
      List<String> scopes = Arrays.asList(scope.split(" "));
      if (!scopes.contains("openid")) {
        throw exception0(BAD_REQUEST.getCode(), "scope 必须包含 openid");
      }

      OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientId, null,
        "authorization_code", new HashSet<>(scopes), redirectUri);
      long currentUserId;
      String prefix;
      if (client.getName().contains("archery")) {
        prefix = OAuth2ClientConstants.ARCHERY_SSO_PREFIX;
      } else if (client.getName().contains("jumpserver")) {
        prefix = OAuth2ClientConstants.JUMPSERVER_SSO_PREFIX;
      } else {
        prefix = OAuth2ClientConstants.RANCHER_SSO_PREFIX;
      }
      String userId = stringRedisTemplate.opsForValue().get(StringUtils.join(prefix, request.getSession().getId()));
      if (StringUtils.isNotBlank(userId)) {
        currentUserId = Long.parseLong(userId);
      } else {
        String loginUrl = getLoginUrl(request) + "?redirect=" +
          encodeUrl(request.getRequestURL().toString() + "?" + request.getQueryString());
        return new ModelAndView("redirect:" + loginUrl);
      }

      if (StringUtils.isNotBlank(nonce)) {
        stringRedisTemplate.opsForValue().set(
          OIDC_NONCE_PREFIX + currentUserId,
          nonce,
          NONCE_EXPIRE_MINUTES, TimeUnit.MINUTES);
      }

      List<String> approvedScopes = new ArrayList<>(scopes);
      String authorizationCode = oauth2GrantService.grantAuthorizationCodeForCode(
        currentUserId, getUserType(), clientId, approvedScopes, redirectUri, state);

      String redirectUrl = OAuth2Utils.buildAuthorizationCodeRedirectUri(redirectUri, authorizationCode, state);
      return new ModelAndView("redirect:" + redirectUrl);
    } catch (Exception e) {
      log.error("OIDC authorize error", e);
      String errorRedirect = buildErrorRedirect(redirectUri, "server_error", e.getMessage(), state);
      return new ModelAndView("redirect:" + errorRedirect);
    }
  }

  @PostMapping("/token")
  @PermitAll
  @Operation(summary = "获取OIDC令牌")
  public Map<String, Object> token(HttpServletRequest request,
                                                 @RequestParam("grant_type") String grantType,
                                                 @RequestParam(value = "code", required = false) String code,
                                                 @RequestParam(value = "redirect_uri", required = false) String redirectUri,
                                                 @RequestParam(value = "client_id", required = false) String clientId,
                                                 @RequestParam(value = "client_secret", required = false) String clientSecret,
                                                 @RequestParam(value = "state", required = false) String state) {
    try {
      String[] clientIdAndSecret;
      if (StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(clientSecret)) {
        clientIdAndSecret = new String[]{clientId, clientSecret};
      } else {
        clientIdAndSecret = HttpUtils.obtainBasicAuthorization(request);
        if (clientIdAndSecret == null || clientIdAndSecret.length != 2) {
          throw exception0(BAD_REQUEST.getCode(), "client_id或client_secret未正确提供");
        }
      }

      OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
        grantType, null, redirectUri);

      if (!"authorization_code".equals(grantType)) {
        throw exception0(BAD_REQUEST.getCode(), "目前仅支持authorization_code授权类型");
      }

      OAuth2AccessTokenDO accessTokenDO = oauth2GrantService.grantAuthorizationCodeForAccessToken(
        client.getClientId(), code, redirectUri, state);

      AdminUserDO user = adminUserService.getUser(accessTokenDO.getUserId());
      if (user == null) {
        throw exception0(BAD_REQUEST.getCode(), "用户不存在");
      }

      String idToken = generateIdToken(accessTokenDO, user, client, request);

      Map<String, Object> respMap = new HashMap<>();
      respMap.put("access_token", accessTokenDO.getAccessToken());
      respMap.put("token_type", "Bearer");
      long expiresIn = java.time.Duration.between(
        java.time.LocalDateTime.now(),
        accessTokenDO.getExpiresTime()).getSeconds();
      respMap.put("expires_in", expiresIn);
      respMap.put("refresh_token", accessTokenDO.getRefreshToken());
      respMap.put("id_token", idToken);
      return respMap;
    } catch (Exception e) {
      log.error("OIDC token error", e);
      throw exception0(BAD_REQUEST.getCode(), "Token获取失败: " + e.getMessage());
    }
  }

  @GetMapping("/userinfo")
  @PermitAll
  @Operation(summary = "获取用户信息")
  public Map<String, Object> userinfo(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
      throw exception0(BAD_REQUEST.getCode(), "缺少有效的Authorization头");
    }

    String accessToken = authorization.substring(7);

    try {
      OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.checkAccessToken(accessToken);
      if (accessTokenDO == null) {
        throw exception0(BAD_REQUEST.getCode(), "无效的访问令牌");
      }

      AdminUserDO user = adminUserService.getUser(accessTokenDO.getUserId());
      if (user == null) {
        throw exception0(BAD_REQUEST.getCode(), "用户不存在");
      }

      Map<String, Object> userInfo = new HashMap<>();
      userInfo.put("sub", String.valueOf(user.getId()));

      List<String> scopes = accessTokenDO.getScopes();
      if (scopes.contains("profile")) {
        userInfo.put("name", user.getNickname());
        userInfo.put("preferred_username", user.getUsername());
      }
      if (scopes.contains("email")) {
        userInfo.put("email", user.getEmail());
        userInfo.put("email_verified", true);
      }

      return userInfo;
    } catch (Exception e) {
      log.error("OIDC userinfo error", e);
      throw exception0(BAD_REQUEST.getCode(), "获取用户信息失败: " + e.getMessage());
    }
  }

  // 修改 JWKS 端点以返回 RSA 公钥
  @GetMapping("/jwks")
  @PermitAll
  @Operation(summary = "获取JWK集合")
  public Map<String, Object> jwks() {
    RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
    Map<String, Object> jwk = new HashMap<>();
    jwk.put("kty", "RSA");
    jwk.put("alg", "RS256");
    jwk.put("kid", "sso-component");
    jwk.put("use", "sig");
    jwk.put("n", base64UrlEncode(rsaPublicKey.getModulus().toByteArray()));
    jwk.put("e", base64UrlEncode(rsaPublicKey.getPublicExponent().toByteArray()));
//    jwk.put("e", "AQAB");
//    jwk.put("n", "vyfZwQuBLNvJDhmziUCFuAfIv-bC6ivodcR6PfanTt8XLd6G63Yx10YChAdsDACjoLz1tEU56WPp_ee_vcTSsEZT3ouWJYghuGI2j4XclXlEj0S7DzdpcBBpI4n5dr8K3iKY-3JUMZR1AMBHI50UaMST9ZTZJAjUPIYxkhRdca5lWBo4wGUh1yj_80-Bq6al0ia9S5NTzNLaJ18jSxFqZ79BAkBm-KjkP248YUk6WBGtYEAV5Fws4dpse4hrqJ3RRHiMZV1o1iTmPHz_l55ZSDP3vpYf6iKqKzoK2RmdjfH5mGpbc4-PclTs4GKfwZ7cWfrny6B7sMnQfzujCH996Q");
//    jwk.put("d", "K2VCm_6enq5uoFLZXUlWkgbCXj5m9X5uUX3_Ol3qcY9X1cP04TN98R8lpw-ASeFDRFRhe0FT-lYCYu_fqZcrNXVhyN3rgi27af5x4HdFMnHLTLMPvE6aEyTGmZjTF1AbiX5VOJAl6POI9FiyTbV1Uqt943ydJv8SH4NfcYhKBmpp8Fi1f58mon-bYwsIy8mzZjssc8KZy-GzpscKrc5ewb7106JY3uRQNprAHrpcGAPZ8uXUvVhrxp_FNn5Nf5KVxl2tm50L83_5nw0OZrbJ8Ceg7sZAw_Z41lbYbS9VDaST6TuKRb7W4XCKimZUn57LoQT2-Gkv6msJHCmqTgK02Q");

    Map<String, Object> jwks = new HashMap<>();
    jwks.put("keys", Collections.singletonList(jwk));

    return jwks;
  }

  // 修改 generateIdToken 使用 RS256 签名
  private String generateIdToken(OAuth2AccessTokenDO accessTokenDO,
                                 AdminUserDO user,
                                 OAuth2ClientDO client,
                                 HttpServletRequest request) throws Exception {
    String nonce = stringRedisTemplate.opsForValue().get(OIDC_NONCE_PREFIX + accessTokenDO.getUserId());

    Map<String, Object> header = new HashMap<>();
    header.put("alg", "RS256");
    header.put("typ", "JWT");
    header.put("kid", "sso-component");

    Map<String, Object> payload = new HashMap<>();
    payload.put("iss", getIssuerUrl(request));
    payload.put("sub", String.valueOf(user.getId()));
    payload.put("aud", client.getClientId());
    long nowSeconds = System.currentTimeMillis() / 1000;
    payload.put("iat", nowSeconds);
    payload.put("exp", nowSeconds + 3600);

    if (StringUtils.isNotBlank(nonce)) {
      payload.put("nonce", nonce);
    }
    payload.put("name", user.getNickname());
    payload.put("preferred_username", user.getUsername());
    if (StringUtils.isNotBlank(user.getEmail())) {
      payload.put("email", user.getEmail());
    }

    String encodedHeader = base64UrlEncode(JsonUtils.toJsonString(header));
    String encodedPayload = base64UrlEncode(JsonUtils.toJsonString(payload));

    // 使用 RS256 签名
    String dataToSign = encodedHeader + "." + encodedPayload;
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);
    signature.update(dataToSign.getBytes("UTF-8"));
    byte[] signatureBytes = signature.sign();
    String encodedSignature = base64UrlEncode(signatureBytes);

    if (StringUtils.isNotBlank(nonce)) {
      stringRedisTemplate.delete(OIDC_NONCE_PREFIX + accessTokenDO.getUserId()); // 修改为 userId
    }

    return encodedHeader + "." + encodedPayload + "." + encodedSignature;
  }

  // 工具方法：加载私钥
  private PrivateKey loadPrivateKey(String pem) throws Exception {
    String keyContent = pem
      .replace("-----BEGIN PRIVATE KEY-----", "")
      .replace("-----END PRIVATE KEY-----", "")
      .replaceAll("\\s+", "");
    byte[] keyBytes = java.util.Base64.getDecoder().decode(keyContent);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(spec);
  }

  // 工具方法：从私钥生成公钥
  private PublicKey generatePublicKeyFromPrivateKey(PrivateKey privateKey) throws Exception {
    KeyFactory kf = KeyFactory.getInstance("RSA");
    java.security.interfaces.RSAPrivateCrtKey privKey = (java.security.interfaces.RSAPrivateCrtKey) privateKey;
    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privKey.getModulus(), privKey.getPublicExponent());
    return kf.generatePublic(publicKeySpec);
  }

  // 修改 base64UrlEncode 支持字节数组
  private String base64UrlEncode(byte[] input) {
    String base64 = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(input);
    return base64;
  }

  private String base64UrlEncode(String input) {
    String base64 = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes());
    return base64;
  }

  // 以下方法保持不变
  private String getLoginUrl(HttpServletRequest request) {
    String serverUrl = getIssuerUrl(request);
    return serverUrl + "/login";
  }

  private String getIssuerUrl(HttpServletRequest request) {
    if (request == null) {
      return "http://172.31.0.6";
    }
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int port = request.getServerPort();
    String path = request.getContextPath();
    StringBuilder sb = new StringBuilder();
    sb.append(scheme).append("://").append(serverName);
    if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
      sb.append(":").append(port);
    }
    sb.append(path);
    return sb.toString();
  }

  private String encodeUrl(String url) {
    try {
      return java.net.URLEncoder.encode(url, "UTF-8");
    } catch (Exception e) {
      return url;
    }
  }

  private String buildErrorRedirect(String redirectUri, String error, String errorDescription, String state) {
    StringBuilder sb = new StringBuilder(redirectUri);
    sb.append(redirectUri.contains("?") ? "&" : "?");
    sb.append("error=").append(encodeUrl(error));
    if (StringUtils.isNotBlank(errorDescription)) {
      sb.append("&error_description=").append(encodeUrl(errorDescription));
    }
    if (StringUtils.isNotBlank(state)) {
      sb.append("&state=").append(encodeUrl(state));
    }
    return sb.toString();
  }

  private Integer getUserType() {
    return UserTypeEnum.ADMIN.getValue();
  }
}
