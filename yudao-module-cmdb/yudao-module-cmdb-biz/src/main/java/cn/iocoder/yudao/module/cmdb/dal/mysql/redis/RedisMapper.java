package cn.iocoder.yudao.module.cmdb.dal.mysql.redis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.RedisPageReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.redis.RedisDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CMDB-Redis Mapper
 *
 * @author 付东阳
 */
@Mapper
public interface RedisMapper extends BaseMapperX<RedisDO> {

    default RedisDO selectByCondition(String instanceName, String cloudArea, String instanceId) {
        return selectOne(RedisDO::getInstanceName, instanceName,
          RedisDO::getCloudArea, cloudArea,
          RedisDO::getInstanceId, instanceId);
    }

    default PageResult<RedisDO> selectPage(RedisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RedisDO>()
                .eqIfPresent(RedisDO::getCloudArea, reqVO.getCloudArea())
                .eqIfPresent(RedisDO::getEnv, reqVO.getEnv())
                .eqIfPresent(RedisDO::getCenter, reqVO.getCenter())
                .eqIfPresent(RedisDO::getTeam, reqVO.getTeam())
                .likeIfPresent(RedisDO::getUser, reqVO.getUser())
                .likeIfPresent(RedisDO::getPromoter, reqVO.getPromoter())
                .likeIfPresent(RedisDO::getInstanceId, reqVO.getInstanceId())
                .likeIfPresent(RedisDO::getInstanceName, reqVO.getInstanceName())
                .likeIfPresent(RedisDO::getHost, reqVO.getHost())
                .likeIfPresent(RedisDO::getHostReadonly, reqVO.getHostReadonly())
                .eqIfPresent(RedisDO::getClusterType, reqVO.getClusterType())
                .eqIfPresent(RedisDO::getPort, reqVO.getPort())
                .eqIfPresent(RedisDO::getPasswd, reqVO.getPasswd())
                .eqIfPresent(RedisDO::getMem, reqVO.getMem())
                .eqIfPresent(RedisDO::getOffline, reqVO.getOffline())
                .eqIfPresent(RedisDO::getLocation, reqVO.getLocation())
                .likeIfPresent(RedisDO::getNotes, reqVO.getNotes())
                .likeIfPresent(RedisDO::getOu, reqVO.getOu())
                .likeIfPresent(RedisDO::getTags, reqVO.getTags())
                .likeIfPresent(RedisDO::getNodesInfo, reqVO.getNodesInfo())
                .likeIfPresent(RedisDO::getExporterIp, reqVO.getExporterIp())
                .likeIfPresent(RedisDO::getExporterPort, reqVO.getExporterPort())
                .eqIfPresent(RedisDO::getMonitored, reqVO.getMonitored())
                .betweenIfPresent(RedisDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RedisDO::getId));
    }

}
