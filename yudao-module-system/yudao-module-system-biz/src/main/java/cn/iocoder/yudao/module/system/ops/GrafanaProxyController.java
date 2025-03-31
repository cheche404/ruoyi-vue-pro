package cn.iocoder.yudao.module.system.ops;

import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

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
//    Cookie cookie = new Cookie("user_id", userId.toString());
//    cookie.setDomain("172.31.0.6");
//    cookie.setPath("/");
//    cookie.setMaxAge(30000);
//    cookie.setSecure(true);
//    cookie.setHttpOnly(true);
//
//    Cookie cookie1 = new Cookie("token", userToken);
//    cookie1.setDomain("172.31.0.6");
//    cookie1.setPath("/");
//    cookie1.setMaxAge(30000);
//    cookie1.setSecure(true);
//    cookie1.setHttpOnly(true);
//    response.addCookie(cookie);
//    response.addCookie(cookie1);
//    response.setHeader("user_id", userId.toString());
    // 重定向到 Grafana OAuth 登录页面
    response.sendRedirect(grafanaRedirectUrl);
  }
}
