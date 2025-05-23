package cn.iocoder.yudao.module.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.util.monitor.TracerUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.module.system.api.sms.SmsCodeApi;
import cn.iocoder.yudao.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.iocoder.yudao.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.module.system.api.social.dto.SocialUserRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.*;
import cn.iocoder.yudao.module.system.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.ldap.DigiwinLdapPerson;
import cn.iocoder.yudao.module.system.dal.dataobject.ldap.LdapPerson;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.DeptMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.enums.ldap.LdapConstants;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import cn.iocoder.yudao.module.system.service.ldap.LdapService;
import cn.iocoder.yudao.module.system.service.logger.LoginLogService;
import cn.iocoder.yudao.module.system.service.member.MemberService;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.yudao.module.system.service.social.SocialUserService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.google.common.annotations.VisibleForTesting;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import com.xingyuv.captcha.util.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private Validator validator;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private LdapTemplate ldapTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private LdapService ldapService;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${yudao.captcha.enable:true}")
    @Setter // 为了单测：开启或者关闭验证码
    private Boolean captchaEnable;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(USER_NOT_EXISTS);
        }
        if (LdapConstants.USER_TYPE_LDAP.equalsIgnoreCase(user.getUserType())) {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter(LdapConstants.LDAP_SAMACCOUNTNAME, username));
            boolean authResult = ldapTemplate.authenticate("", filter.toString(), password);
            if (!authResult) {
                createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
                throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
            }
        } else {
            if (!userService.isPasswordMatch(password, user.getPassword())) {
                createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
                throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
            }
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void ldapValidate(String username, String password) {
        try {
            if ("test".equalsIgnoreCase(springProfilesActive) || "prod".equalsIgnoreCase(springProfilesActive)) {
                AndFilter filter = new AndFilter();
                filter.and(new EqualsFilter(LdapConstants.LDAP_SAMACCOUNTNAME, username));
                boolean authResult = ldapTemplate.authenticate("", filter.toString(), password);
                if (!authResult) {
                    createLoginLog(0L, username, LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.BAD_CREDENTIALS);
                    throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
                }
                DigiwinLdapPerson person = ldapService.findByUsername(username);
                if (Objects.isNull(person)) {
                    throw new EmptyResultDataAccessException("LDAP 返回用户信息为空", 1);
                }
                // 注册用户
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
                // TODO 层级中存在相同的部门名称
                String lastPart = parts[parts.length - 1];
                DeptDO deptDO = deptMapper.selectOne("name", lastPart);
                Long deptId = deptDO.getId();
                userDO.setDeptId(deptId);
                adminUserMapper.insert(userDO);
            } else {
                String baseDn = "ou=users";
                String queryField = "uid";
                Class<?> personClass = LdapPerson.class;
                Function<Object, AdminUserDO> userBuilder = (person) -> buildSysUser((LdapPerson) person, username, password);
                // 构建 LDAP 查询
                LdapQuery query = LdapQueryBuilder.query()
                  .base(baseDn)
                  .where(queryField).is(username);

                // 执行认证
                ldapTemplate.authenticate(query, password);
                // 获取用户信息
                Object person = ldapTemplate.findOne(query, personClass);
                if (Objects.isNull(person)) {
                    throw new EmptyResultDataAccessException("LDAP 返回用户信息为空", 1);
                }
                // 注册用户
                AdminUserDO adminUserDO = userBuilder.apply(person);
                registerUser(adminUserDO);
            }
        } catch (AuthenticationException e) {
            throw new ServiceException(GlobalErrorCodeConstants.LDAP_USER_PASSWORD_ERROR);
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException(GlobalErrorCodeConstants.LDAP_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new ServiceException(GlobalErrorCodeConstants.LDAP_SYNC_ERROR);
        }
    }

    /**
     * 构建 SysUser 对象
     */
    private AdminUserDO buildSysUser(LdapPerson person, String username, String password) {
        AdminUserDO adminUserDO = new AdminUserDO();
        adminUserDO.setUsername(username);
        adminUserDO.setNickname(person.getSn());
        adminUserDO.setPassword(passwordEncoder.encode(password));
        adminUserDO.setEmail(person.getMail());
        adminUserDO.setMobile(person.getMobile());
        adminUserDO.setCreator("ldap");
        HashSet<Long> postIdSet = new HashSet<>();
        postIdSet.add(4L);
        adminUserDO.setPostIds(postIdSet); // 普通角色

        // TODO 设置部门
//        DeptDO dept = new DeptDO();
//        dept.setDeptName(person.getDepartmentNumber());
//        List<DeptDO> depts = deptsMapper.selectDeptList(dept);
//        if (CollectionUtils.isEmpty(depts)) {
//            logger.error("部门: {} 不存在于部门表中", dept.getDeptName());
//            throw new ServiceException("部门 " + dept.getDeptName() + " 不存在，请先添加");
//        }
        adminUserDO.setDeptId(100L);

        return adminUserDO;
    }

    /**
     * 构建 SysUser 对象
     */
    private AdminUserDO buildDigiwinSysUser(DigiwinLdapPerson person, String username, String password) {
        AdminUserDO adminUserDO = new AdminUserDO();
        adminUserDO.setUsername(username);
        adminUserDO.setPassword(passwordEncoder.encode(password));
        adminUserDO.setMobile(person.getMobile());
        adminUserDO.setCreator("ldap");
        HashSet<Long> postIdSet = new HashSet<>();
        postIdSet.add(4L);
        adminUserDO.setPostIds(postIdSet); // 普通角色

        // TODO 设置部门
//        DeptDO dept = new DeptDO();
//        dept.setDeptName(person.getDepartmentNumber());
//        List<DeptDO> depts = deptsMapper.selectDeptList(dept);
//        if (CollectionUtils.isEmpty(depts)) {
//            logger.error("部门: {} 不存在于部门表中", dept.getDeptName());
//            throw new ServiceException("部门 " + dept.getDeptName() + " 不存在，请先添加");
//        }
        adminUserDO.setDeptId(100L);

        return adminUserDO;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha(reqVO);
        // 使用账号密码，进行登录
        AdminUserDO user = null;
        try {
            user = authenticate(reqVO.getUsername(), reqVO.getPassword());
        } catch (ServiceException e) {
            if (e.getMessage().contains("不存在")) {
                // ldap认证，成功后注册
                ldapValidate(reqVO.getUsername(), reqVO.getPassword());
                // 再认证一次
                user = authenticate(reqVO.getUsername(), reqVO.getPassword());
            } else {
                throw e;
            }
        }
        // 如果 socialType 非空，说明需要绑定社交用户
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    /**
     * 注册用户到系统
     */
    private void registerUser(AdminUserDO adminUserDO) {
        Long result = userService.insertUser(adminUserDO);
        if (result == 0) {
            throw new ServiceException(new ErrorCode(222, "注册失败，请联系系统管理员"));
        }
    }

    @Override
    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
        // 如果是重置密码场景，需要校验图形验证码是否正确
        if (Objects.equals(SmsSceneEnum.ADMIN_MEMBER_RESET_PASSWORD.getScene(), reqVO.getScene())) {
            ResponseModel response = doValidateCaptcha(reqVO);
            if (!response.isSuccess()) {
                throw exception(AUTH_REGISTER_CAPTCHA_CODE_ERROR, response.getRepMsg());
            }
        }

        // 登录场景，验证是否存在
        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // 校验验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        SocialUserRespDTO socialUser = socialUserService.getSocialUserByCode(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null || socialUser.getUserId() == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // 获得用户
        AdminUserDO user = userService.getUser(socialUser.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        ResponseModel response = doValidateCaptcha(reqVO);
        // 校验验证码
        if (!response.isSuccess()) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
        }
    }

    private ResponseModel doValidateCaptcha(CaptchaVerificationReqVO reqVO) {
        // 如果验证码关闭，则不进行校验
        if (!captchaEnable) {
            return ResponseModel.success();
        }
        ValidationUtils.validate(validator, reqVO, CaptchaVerificationReqVO.CodeEnableGroup.class);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
        return captchaService.verification(captchaVO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

    @Override
    public AuthLoginRespVO register(AuthRegisterReqVO registerReqVO) {
        // 1. 校验验证码
        validateCaptcha(registerReqVO);

        // 2. 校验用户名是否已存在
        Long userId = userService.registerUser(registerReqVO);

        // 3. 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(userId, registerReqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @VisibleForTesting
    void validateCaptcha(AuthRegisterReqVO reqVO) {
        ResponseModel response = doValidateCaptcha(reqVO);
        // 验证不通过
        if (!response.isSuccess()) {
            throw exception(AUTH_REGISTER_CAPTCHA_CODE_ERROR, response.getRepMsg());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(AuthResetPasswordReqVO reqVO) {
        AdminUserDO userByMobile = userService.getUserByMobile(reqVO.getMobile());
        if (userByMobile == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }

        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO()
                .setCode(reqVO.getCode())
                .setMobile(reqVO.getMobile())
                .setScene(SmsSceneEnum.ADMIN_MEMBER_RESET_PASSWORD.getScene())
                .setUsedIp(getClientIP())
        );

        userService.updateUserPassword(userByMobile.getId(), reqVO.getPassword());
    }
}
