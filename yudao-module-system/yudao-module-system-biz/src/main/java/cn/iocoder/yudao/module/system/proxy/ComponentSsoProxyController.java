package cn.iocoder.yudao.module.system.proxy;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @author fudy
 * @date 2025/3/29
 */
@Slf4j
@RestController
@RequestMapping("/admin-api/component-sso-proxy")
public class ComponentSsoProxyController {

  @Value("${sso.grafana_callback_url}")
  private String grafanaCallbackUrl;

  private final OAuth2TokenService oauth2TokenService;

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  public ComponentSsoProxyController(OAuth2TokenService oauth2TokenService) {
    this.oauth2TokenService = oauth2TokenService;
  }

  @GetMapping("/sso")
  public CommonResult<String> redirectToGrafana(@RequestParam("token") String userToken,
                                HttpServletResponse response,
                                HttpServletRequest request) {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    System.out.println("======sso========userid==========" + userId + "=======session=======" + request.getSession().getId());
    // 拼接 OAuth 登录 URL，并附加 state 参数
    String grafanaRedirectUrl = grafanaCallbackUrl + "?user_id=" + userId;
//    response.sendRedirect(grafanaRedirectUrl);
    return success("success");
  }

  @GetMapping("/sso-archery")
  public CommonResult<String> redirectToArchery(@RequestParam("token") String userToken,
                                                HttpServletRequest request) {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    String sessionId = request.getSession().getId();
    if (StringUtils.isNotBlank(sessionId) && userId != null) {
      // 把 userId 绑定到 session 供后续使用
      stringRedisTemplate.opsForValue().set(
        OAuth2ClientConstants.ARCHERY_SSO_PREFIX + sessionId,
        userId.toString(),
        24, TimeUnit.HOURS
      );

      // 直接返回给前端一个要加载的 Archery 登录 URL
      String redirectUrl = "http://172.31.0.7:9123/oidc/callback";
      return CommonResult.success(redirectUrl);
    }

    return success("success");
  }


  @GetMapping("/sso-jumpserver")
  public CommonResult<String> redirectToJumpserver(@RequestParam("token") String userToken,
                                                HttpServletRequest request) {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    String sessionId = request.getSession().getId();
    if (StringUtils.isNotBlank(sessionId) && !Objects.isNull(userId)) {
      stringRedisTemplate.opsForValue().set(
        StringUtils.join(OAuth2ClientConstants.JUMPSERVER_SSO_PREFIX, request.getSession().getId()),
        userId.toString(),
        24,
        TimeUnit.HOURS);
    }
    return success("success");
  }

  @GetMapping("/sso-rancher")
  public CommonResult<String> redirectToRancher(@RequestParam("token") String userToken,
                                                   HttpServletRequest request, HttpServletResponse response) {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    String sessionId = request.getSession().getId();
    System.out.println("-----------sso-rancher--------------sessionId------------------------" + sessionId);

    // 设置 Cookie
    Cookie cookie = new Cookie("JSESSIONID", sessionId); // 替换为你的 Cookie 名称和值
    cookie.setPath("/"); // 设置 Cookie 路径
    cookie.setDomain("digiwincloud.com.cn");
    cookie.setMaxAge(60 * 60); // 设置 Cookie 有效期，例如 1 小时
    cookie.setHttpOnly(true); // 提高安全性，防止客户端脚本访问
    cookie.setSecure(request.isSecure()); // 仅在 HTTPS 下发送
    response.addCookie(cookie); // 将 Cookie 添加到响应

    if (StringUtils.isNotBlank(sessionId) && !Objects.isNull(userId)) {
      stringRedisTemplate.opsForValue().set(
        StringUtils.join(OAuth2ClientConstants.RANCHER_SSO_PREFIX, request.getSession().getId()),
        userId.toString(),
        24,
        TimeUnit.HOURS);
    }
    return success("success");
  }

}
