package cn.iocoder.yudao.module.cmdb.service.mongodb;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.MongodbImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.MongodbImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.MongodbPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.MongodbSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mongodb.MongodbDO;
import cn.iocoder.yudao.module.cmdb.dal.mysql.mongodb.MongodbMapper;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * CMDB-MongoDB Service 实现类
 *
 * @author OPS管理员
 */
@Service
@Validated
public class MongodbServiceImpl implements MongodbService {

    @Resource
    private MongodbMapper mongodbMapper;

    @Override
    public Long createMongodb(MongodbSaveReqVO createReqVO) {
        // 插入
        MongodbDO mongodb = BeanUtils.toBean(createReqVO, MongodbDO.class);
        mongodbMapper.insert(mongodb);
        // 返回
        return mongodb.getId();
    }

    @Override
    public void updateMongodb(MongodbSaveReqVO updateReqVO) {
        // 校验存在
        validateMongodbExists(updateReqVO.getId());
        // 更新
        MongodbDO updateObj = BeanUtils.toBean(updateReqVO, MongodbDO.class);
        mongodbMapper.updateById(updateObj);
    }

    @Override
    public void deleteMongodb(Long id) {
        // 校验存在
        validateMongodbExists(id);
        // 删除
        mongodbMapper.deleteById(id);
    }

    private void validateMongodbExists(Long id) {
        if (mongodbMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.MONGODB_NOT_EXISTS);
        }
    }

    @Override
    public MongodbDO getMongodb(Long id) {
        return mongodbMapper.selectById(id);
    }

    @Override
    public PageResult<MongodbDO> getMongodbPage(MongodbPageReqVO pageReqVO) {
        return mongodbMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public MongodbImportRespVO importMongodbList(List<MongodbImportExcelVO> importMongodbs, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importMongodbs)) {
            throw exception(ErrorCodeConstants.MONGODB_IMPORT_LIST_IS_EMPTY);
        }

        // 2. 遍历，逐个创建 or 更新
        MongodbImportRespVO respVO = MongodbImportRespVO.builder().createInstanceNames(new ArrayList<>())
          .updateInstanceNames(new ArrayList<>()).failureInstanceNames(new LinkedHashMap<>()).build();
        importMongodbs.forEach(importMongodb -> {
            // 2.1.1 判断如果不存在，在进行插入
            MongodbDO existMongodb = mongodbMapper.selectByCondition(importMongodb.getInstanceName(),
              importMongodb.getCloudArea(), importMongodb.getInstanceId());
            if (existMongodb == null) {
                mongodbMapper.insert(BeanUtils.toBean(importMongodb, MongodbDO.class));
                respVO.getCreateInstanceNames().add(importMongodb.getInstanceName());
                return;
            }
            // 2.2.1 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureInstanceNames().put(importMongodb.getInstanceName(),
                  ErrorCodeConstants.MONGODB_INSTANCE_NAME_EXISTS.getMsg());
                return;
            }
            MongodbDO updateMongodb = BeanUtils.toBean(importMongodb, MongodbDO.class);
            updateMongodb.setId(existMongodb.getId());
            mongodbMapper.updateById(updateMongodb);
            respVO.getUpdateInstanceNames().add(importMongodb.getInstanceName());
        });
        return respVO;
    }

}
