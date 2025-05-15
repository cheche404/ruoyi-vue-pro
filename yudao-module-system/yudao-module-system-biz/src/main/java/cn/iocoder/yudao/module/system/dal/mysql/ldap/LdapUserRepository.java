package cn.iocoder.yudao.module.system.dal.mysql.ldap;

import cn.iocoder.yudao.module.system.dal.dataobject.ldap.DigiwinLdapPerson;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fudy
 * @date 2025/5/13
 */
@Repository
public interface LdapUserRepository extends LdapRepository<DigiwinLdapPerson> {

  DigiwinLdapPerson findByUsername(String username);
}
