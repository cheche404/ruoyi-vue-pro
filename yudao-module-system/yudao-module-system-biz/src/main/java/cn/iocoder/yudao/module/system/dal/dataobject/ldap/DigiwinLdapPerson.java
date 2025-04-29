package cn.iocoder.yudao.module.system.dal.dataobject.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

import javax.naming.Name;

/**
 * @author fudy
 * @date 2025/3/26
 */
@Data
@Entry(base = "ou=users ", objectClasses = "inetOrgPerson")
public class DigiwinLdapPerson {

  private static final long serialVersionUID = 1L;

  @org.springframework.ldap.odm.annotations.Id
  @JsonIgnore
  private Name dn;

  @Attribute(name = "mail")
  private String mail;

  @Attribute(name = "name")
  private String name;

  @Attribute(name = "description")
  private String description;

  @Attribute(name = "sn")
  private String sn;

  @Attribute(name = "cn")
  private String cn;

  @Attribute(name = "title")
  private String title;

  @Attribute(name = "mobile")
  private String mobile;

  @Attribute(name = "givenName")
  private String givenName;

  @Attribute(name = "sAMAccountName")
  private String sAMAccountName;

  @Attribute(name = "displayName")
  private String displayName;

  @Attribute(name = "userPrincipalName")
  private String userPrincipalName;

  @Attribute(name = "department")
  private String department;

  @Attribute(name = "distinguishedName")
  private String distinguishedName;

}
