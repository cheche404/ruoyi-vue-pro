package cn.iocoder.yudao.module.system.dal.dataobject.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;

import javax.naming.Name;

/**
 * @author fudy
 * @date 2025/3/26
 */
@Entry(base = "ou=users ", objectClasses = "inetOrgPerson")
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

  public Name getDn() {
    return dn;
  }

  public void setDn(Name dn) {
    this.dn = dn;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getCn() {
    return cn;
  }

  public void setCn(String cn) {
    this.cn = cn;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getBusinessCategory() {
    return businessCategory;
  }

  public void setBusinessCategory(String businessCategory) {
    this.businessCategory = businessCategory;
  }

  public String getDepartmentNumber() {
    return departmentNumber;
  }

  public void setDepartmentNumber(String departmentNumber) {
    this.departmentNumber = departmentNumber;
  }
}
