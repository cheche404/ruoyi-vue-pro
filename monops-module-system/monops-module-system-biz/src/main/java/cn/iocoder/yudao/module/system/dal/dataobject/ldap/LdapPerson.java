package cn.iocoder.yudao.module.system.dal.dataobject.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;

import javax.naming.Name;

/**
 * @author fudy
 * @date 2025/3/26
 */
@Data
@Entry(base = "ou=users", objectClasses = "inetOrgPerson")
public class LdapPerson {

  private static final long serialVersionUID = 1L;

  @org.springframework.ldap.odm.annotations.Id
  @JsonIgnore
  private Name dn;

  @DnAttribute(value = "uid")
  private String uid;

  @Attribute(name = "cn")
  private String cn;

  @Attribute(name = "sn")
  private String sn;

  @Attribute(name = "mobile")
  private String mobile;

  @Attribute(name = "mail")
  private String mail;

  @Attribute(name = "businessCategory")
  private String businessCategory;

  @Attribute(name = "departmentNumber")
  private String departmentNumber;

}
