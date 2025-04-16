package cn.iocoder.yudao.module.cmdb.service.host;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.cmdb.controller.admin.host.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.host.HostDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.cmdb.dal.mysql.host.HostMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * CMDB主机 Service 实现类
 *
 * @author 付东阳
 */
@Service
@Validated
public class HostServiceImpl implements HostService {

    @Resource
    private HostMapper hostMapper;

    @Override
    public Long createHost(HostSaveReqVO createReqVO) {
        // 插入
        HostDO host = BeanUtils.toBean(createReqVO, HostDO.class);
        hostMapper.insert(host);
        // 返回
        return host.getId();
    }

    @Override
    public void updateHost(HostSaveReqVO updateReqVO) {
        // 校验存在
        validateHostExists(updateReqVO.getId());
        // 更新
        HostDO updateObj = BeanUtils.toBean(updateReqVO, HostDO.class);
        hostMapper.updateById(updateObj);
    }

    @Override
    public void deleteHost(Long id) {
        // 校验存在
        validateHostExists(id);
        // 删除
        hostMapper.deleteById(id);
    }

    private void validateHostExists(Long id) {
        if (hostMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.HOST_NOT_EXISTS);
        }
    }

    @Override
    public HostDO getHost(Long id) {
        return hostMapper.selectById(id);
    }

    @Override
    public PageResult<HostDO> getHostPage(HostPageReqVO pageReqVO) {
        return hostMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public HostImportRespVO importHostList(List<HostImportExcelVO> importHosts, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importHosts)) {
            throw exception(ErrorCodeConstants.HOST_IMPORT_LIST_IS_EMPTY);
        }

        // 2. 遍历，逐个创建 or 更新
        HostImportRespVO respVO = HostImportRespVO.builder().createInstanceNames(new ArrayList<>())
          .updateInstanceNames(new ArrayList<>()).failureInstanceNames(new LinkedHashMap<>()).build();
        importHosts.forEach(importHost -> {
            // 2.1.1 判断如果不存在，在进行插入
            HostDO existHost = hostMapper.selectByCondition(importHost.getInstanceName(),
               importHost.getCloudArea(), importHost.getInstanceId());
            if (existHost == null) {
                hostMapper.insert(BeanUtils.toBean(importHost, HostDO.class));
                respVO.getCreateInstanceNames().add(importHost.getInstanceName());
                return;
            }
            // 2.2.1 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureInstanceNames().put(importHost.getInstanceName(),
                  ErrorCodeConstants.HOST_INSTANCE_NAME_EXISTS.getMsg());
                return;
            }
            HostDO updateHost = BeanUtils.toBean(importHost, HostDO.class);
            updateHost.setId(existHost.getId());
            hostMapper.updateById(updateHost);
            respVO.getUpdateInstanceNames().add(importHost.getInstanceName());
        });
        return respVO;
    }

}
