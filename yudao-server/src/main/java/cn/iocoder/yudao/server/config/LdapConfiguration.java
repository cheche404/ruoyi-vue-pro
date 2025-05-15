package cn.iocoder.yudao.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;

/**
 * @author fudy
 * @date 2025/3/26
 */
@Configuration
public class LdapConfiguration {

  @Bean
  public LdapTemplate ldapTemplate(ContextSource contextSource) {
    return new LdapTemplate(contextSource);
  }

}
