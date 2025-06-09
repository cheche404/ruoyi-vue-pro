package cn.iocoder.yudao.module.cmdb.service.namespace;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportRespVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mq.MqDO;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.namespace.NamespaceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.cmdb.dal.mysql.namespace.NamespaceMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants.*;

/**
 * CMDB-namespace Service 实现类
 *
 * @author admin
 */
@Service
@Validated
public class NamespaceServiceImpl implements NamespaceService {

    @Resource
    private NamespaceMapper namespaceMapper;

    @Override
    public Long createNamespace(NamespaceSaveReqVO createReqVO) {
        // 插入
        NamespaceDO namespace = BeanUtils.toBean(createReqVO, NamespaceDO.class);
        namespaceMapper.insert(namespace);
        // 返回
        return namespace.getId();
    }

    @Override
    public void updateNamespace(NamespaceSaveReqVO updateReqVO) {
        // 校验存在
        validateNamespaceExists(updateReqVO.getId());
        // 更新
        NamespaceDO updateObj = BeanUtils.toBean(updateReqVO, NamespaceDO.class);
        namespaceMapper.updateById(updateObj);
    }

    @Override
    public void deleteNamespace(Long id) {
        // 校验存在
        validateNamespaceExists(id);
        // 删除
        namespaceMapper.deleteById(id);
    }

    private void validateNamespaceExists(Long id) {
        if (namespaceMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.NAMESPACE_NOT_EXISTS);
        }
    }

    @Override
    public NamespaceDO getNamespace(Long id) {
        return namespaceMapper.selectById(id);
    }

    @Override
    public PageResult<NamespaceDO> getNamespacePage(NamespacePageReqVO pageReqVO) {
        return namespaceMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public NamespaceImportRespVO importNamespaceList(List<NamespaceImportExcelVO> importNamespaces, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importNamespaces)) {
            throw exception(ErrorCodeConstants.NAMESPACE_IMPORT_LIST_IS_EMPTY);
        }

        // 2. 遍历，逐个创建 or 更新
        NamespaceImportRespVO respVO = NamespaceImportRespVO.builder().createNamespaceNames(new ArrayList<>())
          .updateNamespaceNames(new ArrayList<>()).failureNamespaceNames(new LinkedHashMap<>()).build();
        importNamespaces.forEach(importNamespace -> {
            // 2.1.1 判断如果不存在，在进行插入
            NamespaceDO existNamespace = namespaceMapper.selectByCondition(importNamespace.getNamespace(),
              importNamespace.getCloudArea(), importNamespace.getEnv());
            if (existNamespace == null) {
                namespaceMapper.insert(BeanUtils.toBean(importNamespace, NamespaceDO.class));
                respVO.getCreateNamespaceNames().add(importNamespace.getNamespace());
                return;
            }
            // 2.2.1 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureNamespaceNames().put(importNamespace.getNamespace(),
                  ErrorCodeConstants.NAMESPACE_CLUSTER_NAME_EXISTS.getMsg());
                return;
            }
            NamespaceDO updateNamespace = BeanUtils.toBean(importNamespace, NamespaceDO.class);
            updateNamespace.setId(existNamespace.getId());
            namespaceMapper.updateById(updateNamespace);
            respVO.getUpdateNamespaceNames().add(importNamespace.getNamespace());
        });
        return respVO;
    }

}
