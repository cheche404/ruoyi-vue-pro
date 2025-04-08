package cn.iocoder.yudao.module.system.ops;

import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fudy
 * @date 2025/3/29
 */
@RestController
@RequestMapping("/admin-api/grafana-proxy")
public class GrafanaProxyController {

  private static final String GRAFANA_OAUTH_LOGIN_URL = "http://172.31.0.6/grafana/login/generic_oauth";

  private final OAuth2TokenService oauth2TokenService;

  public GrafanaProxyController(OAuth2TokenService oauth2TokenService) {
    this.oauth2TokenService = oauth2TokenService;
  }

  @GetMapping("/sso")
  public void redirectToGrafana(@RequestParam("token") String userToken, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
    Long userId = oauth2TokenService.getAccessToken(userToken).getUserId();
    // 拼接 OAuth 登录 URL，并附加 state 参数
    String grafanaRedirectUrl = GRAFANA_OAUTH_LOGIN_URL + "?user_id=" + userId;
    response.sendRedirect(grafanaRedirectUrl);
  }
}
