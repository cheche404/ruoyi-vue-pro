package cn.iocoder.yudao.module.system.service.ldap;

import cn.iocoder.yudao.module.system.dal.dataobject.ldap.DigiwinLdapPerson;

import java.util.List;

/**
 * @author fudy
 * @date 2025/5/13
 */
public interface LdapService {

  DigiwinLdapPerson findByUsername(String username);

  List<DigiwinLdapPerson> findAll();

  boolean authenticate(String username, String password);

  void updateAllDepartmentAndUsers();

}
