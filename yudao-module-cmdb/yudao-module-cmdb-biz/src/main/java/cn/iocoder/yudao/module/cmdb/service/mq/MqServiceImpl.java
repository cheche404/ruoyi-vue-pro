package cn.iocoder.yudao.module.cmdb.service.mq;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mq.MqDO;
import cn.iocoder.yudao.module.cmdb.dal.mysql.mq.MqMapper;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants.MQ_NOT_EXISTS;

/**
 * CMDB-MQ Service 实现类
 *
 * @author admin
 */
@Service
@Validated
public class MqServiceImpl implements MqService {

    @Resource
    private MqMapper mqMapper;

    @Override
    public Long createMq(MqSaveReqVO createReqVO) {
        // 插入
        MqDO mq = BeanUtils.toBean(createReqVO, MqDO.class);
        mqMapper.insert(mq);
        // 返回
        return mq.getId();
    }

    @Override
    public void updateMq(MqSaveReqVO updateReqVO) {
        // 校验存在
        validateMqExists(updateReqVO.getId());
        // 更新
        MqDO updateObj = BeanUtils.toBean(updateReqVO, MqDO.class);
        mqMapper.updateById(updateObj);
    }

    @Override
    public void deleteMq(Long id) {
        // 校验存在
        validateMqExists(id);
        // 删除
        mqMapper.deleteById(id);
    }

    private void validateMqExists(Long id) {
        if (mqMapper.selectById(id) == null) {
            throw exception(MQ_NOT_EXISTS);
        }
    }

    @Override
    public MqDO getMq(Long id) {
        return mqMapper.selectById(id);
    }

    @Override
    public PageResult<MqDO> getMqPage(MqPageReqVO pageReqVO) {
        return mqMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public MqImportRespVO importMqList(List<MqImportExcelVO> importMqs, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importMqs)) {
            throw exception(ErrorCodeConstants.MQ_IMPORT_LIST_IS_EMPTY);
        }

        // 2. 遍历，逐个创建 or 更新
        MqImportRespVO respVO = MqImportRespVO.builder().createClusterNames(new ArrayList<>())
          .updateClusterNames(new ArrayList<>()).failureClusterNames(new LinkedHashMap<>()).build();
        importMqs.forEach(importMq -> {
            // 2.1.1 判断如果不存在，在进行插入
            MqDO existMq = mqMapper.selectByCondition(importMq.getClusterName(),
              importMq.getCloudArea(), importMq.getHost());
            if (existMq == null) {
                mqMapper.insert(BeanUtils.toBean(importMq, MqDO.class));
                respVO.getCreateClusterNames().add(importMq.getClusterName());
                return;
            }
            // 2.2.1 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureClusterNames().put(importMq.getClusterName(),
                  ErrorCodeConstants.MQ_CLUSTER_NAME_EXISTS.getMsg());
                return;
            }
            MqDO updateMq = BeanUtils.toBean(importMq, MqDO.class);
            updateMq.setId(existMq.getId());
            mqMapper.updateById(updateMq);
            respVO.getUpdateClusterNames().add(importMq.getClusterName());
        });
        return respVO;
    }

}
