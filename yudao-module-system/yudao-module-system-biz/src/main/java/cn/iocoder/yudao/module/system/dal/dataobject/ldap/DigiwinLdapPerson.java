package cn.iocoder.yudao.module.system.dal.dataobject.ldap;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

/**
 * @author fudy
 * @date 2025/3/26
 */
@Data
@Entry(objectClasses = {"top", "person", "organizationalPerson", "user"})
public class DigiwinLdapPerson {

  @Id
  private Name dn;

  @Attribute(name = "cn")
  private String cn;

  @Attribute(name = "sn")
  private String sn;

  @Attribute(name = "sAMAccountName")
  private String username;

  @Attribute(name = "name")
  private String name;

  @Attribute(name = "userPrincipalName")
  private String userPrincipalName;

  @Attribute(name = "givenName")
  private String givenName;

  @Attribute(name = "displayName")
  private String displayName;

  @Attribute(name = "mail")
  private String email;

  @Attribute(name = "mobile")
  private String mobile;

  @Attribute(name = "department")
  private String department;

  @Attribute(name = "title")
  private String title;

  @Attribute(name = "description")
  private String description;

  @Attribute(name = "memberOf")
  private String[] memberOf;

}
