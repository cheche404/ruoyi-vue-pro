package cn.iocoder.yudao.module.system.service.ldap;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.ldap.DigiwinLdapPerson;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.DeptMapper;
import cn.iocoder.yudao.module.system.dal.mysql.ldap.LdapUserRepository;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.enums.ldap.LdapConstants;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fudy
 * @date 2025/5/14
 */
@Service
@Validated
public class LdapServiceImpl implements LdapService {

  @Resource
  private LdapUserRepository ldapUserRepository;
  @Resource
  private LdapTemplate ldapTemplate;
  @Resource
  private DeptMapper deptMapper;
  @Resource
  private AdminUserMapper adminUserMapper;

  public DigiwinLdapPerson findByUsername(String username) {
    return ldapUserRepository.findByUsername(username);
  }

  public List<DigiwinLdapPerson> findAll() {
    return ldapUserRepository.findAll();
  }

  public boolean authenticate(String username, String password) {
    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter(LdapConstants.LDAP_SAMACCOUNTNAME, username));

    return ldapTemplate.authenticate("", filter.toString(), password);
  }

  public void updateAllDepartmentAndUsers() {
    List<DigiwinLdapPerson> list = ldapUserRepository.findAll();
    Set<String> deptSets = new HashSet<>();

    // 过滤掉 null 和空白部门
    for (DigiwinLdapPerson person : list) {
      if (StringUtils.isNotBlank(person.getDepartment())) {
        deptSets.add(person.getDepartment().trim());
      }
    }

    // 添加 其他 部门，存放外包等人员
    deptSets.add(LdapConstants.OTHER_DEPT_NAME);

    // 用于记录已经插入的部门结构，避免重复插入
    Map<String, Long> deptPathToIdMap = new HashMap<>();
    Map<String, Long> deptToIdMap = new HashMap<>();

    for (String dept : deptSets) {
      String[] parts = dept.split("\\.");
      StringBuilder pathBuilder = new StringBuilder();
      Long parentId = 1L;

      for (String part : parts) {
        if (pathBuilder.length() > 0) {
          pathBuilder.append(".");
        }
        pathBuilder.append(part);
        String currentPath = pathBuilder.toString();

        // 如果已插入过该部门路径，直接跳过
        if (deptPathToIdMap.containsKey(currentPath)) {
          parentId = deptPathToIdMap.get(currentPath);
          continue;
        }

        // 插入数据库
        DeptDO deptDO = new DeptDO();
        deptDO.setName(part);
        deptDO.setStatus(0);
        deptDO.setLeaderUserId(1L);
        deptDO.setParentId(parentId);
        deptDO.setTenantId(1L);
        deptMapper.insert(deptDO);

        parentId = deptDO.getId();
        deptPathToIdMap.put(currentPath, parentId);
        deptToIdMap.put(part, parentId);
      }
    }

    List<AdminUserDO> userDOS = new ArrayList<>();
    // 处理人员
    for (DigiwinLdapPerson person : list) {
      if (StringUtils.isBlank(person.getDepartment())) {
        person.setDepartment(LdapConstants.OTHER_DEPT_NAME);
      }
      AdminUserDO userDO = new AdminUserDO();
      userDO.setCn(person.getCn());
      userDO.setNickname(person.getCn());
      userDO.setSn(person.getSn());
      userDO.setUsername(person.getUsername());
      userDO.setUserPrincipalName(person.getUserPrincipalName());
      userDO.setEmail(person.getEmail());
      userDO.setMobile(person.getMobile());
      userDO.setDisplayName(person.getDisplayName());
      userDO.setGivenName(person.getGivenName());
      userDO.setDeleted(false);
      userDO.setUserType(LdapConstants.USER_TYPE_LDAP);
      userDO.setTenantId(1L);
      // 处理部门
      String[] parts = person.getDepartment().split("\\.");
      String lastPart = parts[parts.length - 1];
      Long deptId = deptToIdMap.get(lastPart);
      userDO.setDeptId(deptId);
      userDOS.add(userDO);
    }
    adminUserMapper.insert(userDOS);
  }

}
