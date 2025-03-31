package cn.iocoder.yudao.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fudy
 * @date 2025/3/26
 */
@Configuration
public class LdapConfiguration {

  @Value("${spring.ldap.urls}")
  private String urls;
  @Value("${spring.ldap.base}")
  private String base;
  @Value("${spring.ldap.username}")
  private String username;
  @Value("${spring.ldap.password}")
  private String password;

  private LdapTemplate ldapTemplate;

  @Bean
  public LdapContextSource contextSource() {
    LdapContextSource contextSource = new LdapContextSource();
    Map<String, Object> config = new HashMap<>();
    // 处理乱码
    config.put("java.naming.ldap.attributes.binary", "objectGUID");
    contextSource.setUrl(urls);
    contextSource.setBase(base);
    contextSource.setUserDn(username);
    contextSource.setPassword(password);
    // 不使用已经创建好连接
    //  如果使用已经创建连接，去验证，总是失败的
    contextSource.setPooled(true);
    contextSource.setBaseEnvironmentProperties(config);
    // 验证必填参数都设置好
    contextSource.afterPropertiesSet();
    return contextSource;
  }

  @Bean
  public LdapTemplate ldapTemplate() {
    if (ldapTemplate == null) {
      ldapTemplate = new LdapTemplate(contextSource());
    }
    return ldapTemplate;
  }

}
