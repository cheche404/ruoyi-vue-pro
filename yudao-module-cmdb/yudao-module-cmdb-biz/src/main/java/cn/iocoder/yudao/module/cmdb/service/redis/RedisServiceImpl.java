package cn.iocoder.yudao.module.cmdb.service.redis;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportRespVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mysql.MysqlDO;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.redis.RedisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.cmdb.dal.mysql.redis.RedisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants.*;

/**
 * CMDB-Redis Service 实现类
 *
 * @author 付东阳
 */
@Service
@Validated
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisMapper redisMapper;

    @Override
    public Long createRedis(RedisSaveReqVO createReqVO) {
        // 插入
        RedisDO redis = BeanUtils.toBean(createReqVO, RedisDO.class);
        redisMapper.insert(redis);
        // 返回
        return redis.getId();
    }

    @Override
    public void updateRedis(RedisSaveReqVO updateReqVO) {
        // 校验存在
        validateRedisExists(updateReqVO.getId());
        // 更新
        RedisDO updateObj = BeanUtils.toBean(updateReqVO, RedisDO.class);
        redisMapper.updateById(updateObj);
    }

    @Override
    public void deleteRedis(Long id) {
        // 校验存在
        validateRedisExists(id);
        // 删除
        redisMapper.deleteById(id);
    }

    private void validateRedisExists(Long id) {
        if (redisMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.REDIS_NOT_EXISTS);
        }
    }

    @Override
    public RedisDO getRedis(Long id) {
        return redisMapper.selectById(id);
    }

    @Override
    public PageResult<RedisDO> getRedisPage(RedisPageReqVO pageReqVO) {
        return redisMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public RedisImportRespVO importRedisList(List<RedisImportExcelVO> importRedises, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importRedises)) {
            throw exception(ErrorCodeConstants.REDIS_IMPORT_LIST_IS_EMPTY);
        }

        // 2. 遍历，逐个创建 or 更新
        RedisImportRespVO respVO = RedisImportRespVO.builder().createInstanceNames(new ArrayList<>())
          .updateInstanceNames(new ArrayList<>()).failureInstanceNames(new LinkedHashMap<>()).build();
        importRedises.forEach(importRedis -> {
            // 2.1.1 判断如果不存在，在进行插入
            RedisDO existMysql = redisMapper.selectByCondition(importRedis.getInstanceName(),
              importRedis.getCloudArea(), importRedis.getInstanceId());
            if (existMysql == null) {
                redisMapper.insert(BeanUtils.toBean(importRedis, RedisDO.class));
                respVO.getCreateInstanceNames().add(importRedis.getInstanceName());
                return;
            }
            // 2.2.1 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureInstanceNames().put(importRedis.getInstanceName(),
                  ErrorCodeConstants.REDIS_INSTANCE_NAME_EXISTS.getMsg());
                return;
            }
            RedisDO updateRedis = BeanUtils.toBean(importRedis, RedisDO.class);
            updateRedis.setId(existMysql.getId());
            redisMapper.updateById(updateRedis);
            respVO.getUpdateInstanceNames().add(importRedis.getInstanceName());
        });
        return respVO;
    }

}
