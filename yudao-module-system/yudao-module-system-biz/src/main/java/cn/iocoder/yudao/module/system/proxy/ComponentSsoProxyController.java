package cn.iocoder.yudao.module.system.proxy;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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

  private static final String GRAFANA_OAUTH_LOGIN_URL = "http://172.31.0.6/grafana/login/generic_oauth";

  private final OAuth2TokenService oauth2TokenService;

  @Resource
  private StringRedisTemplate stringRedisTemplate;
  @Resource
  private RestTemplate restTemplate;

  public ComponentSsoProxyController(OAuth2TokenService oauth2TokenService) {
    this.oauth2TokenService = oauth2TokenService;
  }

  @GetMapping("/sso")
  public void redirectToGrafana(@RequestParam("token") String userToken,
                                HttpServletResponse response) throws IOException {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    // 拼接 OAuth 登录 URL，并附加 state 参数
    String grafanaRedirectUrl = GRAFANA_OAUTH_LOGIN_URL + "?user_id=" + userId;
    response.sendRedirect(grafanaRedirectUrl);
  }

  @GetMapping("/sso-archery")
  public CommonResult<String> redirectToArchery(@RequestParam("token") String userToken,
                                                HttpServletRequest request) {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    String sessionId = request.getSession().getId();
    if (StringUtils.isNotBlank(sessionId) && !Objects.isNull(userId)) {
      stringRedisTemplate.opsForValue().set(
        StringUtils.join(OAuth2ClientConstants.ARCHERY_SSO_PREFIX, request.getSession().getId()),
        userId.toString(),
        24,
        TimeUnit.HOURS);
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


}
