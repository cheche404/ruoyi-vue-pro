package cn.iocoder.yudao.module.system.controller.admin.ldap;

import cn.iocoder.yudao.module.system.dal.dataobject.ldap.DigiwinLdapPerson;
import cn.iocoder.yudao.module.system.service.ldap.LdapService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fudy
 * @date 2025/5/13
 */
@RestController
@RequestMapping("/system/ldap")
public class LdapController {
  @Resource
  private LdapService ldapService;

  @GetMapping("/user/{username}")
  public DigiwinLdapPerson getUserByUsername(@PathVariable String username) {
    return ldapService.findByUsername(username);
  }

  @GetMapping("/users")
  public Iterable<DigiwinLdapPerson> getAllUsers() {
    return ldapService.findAll();
  }

  @GetMapping("/users/update")
  public void updateAllDepartmentAndUsers() {
    ldapService.updateAllDepartmentAndUsers();
  }

  @PostMapping("/authenticate")
  public boolean authenticate(@RequestParam String username,
                              @RequestParam String password) {
    return ldapService.authenticate(username, password);
  }
}
