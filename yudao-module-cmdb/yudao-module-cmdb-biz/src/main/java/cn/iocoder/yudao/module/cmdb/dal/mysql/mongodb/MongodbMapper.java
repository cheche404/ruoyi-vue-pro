package cn.iocoder.yudao.module.cmdb.dal.mysql.mongodb;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.MongodbPageReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mongodb.MongodbDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CMDB-MongoDB Mapper
 *
 * @author OPS管理员
 */
@Mapper
public interface MongodbMapper extends BaseMapperX<MongodbDO> {

    default MongodbDO selectByCondition(String instanceName, String cloudArea, String instanceId) {
        return selectOne(MongodbDO::getInstanceName, instanceName,
          MongodbDO::getCloudArea, cloudArea,
          MongodbDO::getInstanceId, instanceId);
    }

    default PageResult<MongodbDO> selectPage(MongodbPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MongodbDO>()
                .likeIfPresent(MongodbDO::getCloudArea, reqVO.getCloudArea())
                .likeIfPresent(MongodbDO::getEnv, reqVO.getEnv())
                .likeIfPresent(MongodbDO::getCenter, reqVO.getCenter())
                .likeIfPresent(MongodbDO::getTeam, reqVO.getTeam())
                .likeIfPresent(MongodbDO::getUser, reqVO.getUser())
                .likeIfPresent(MongodbDO::getPromoter, reqVO.getPromoter())
                .likeIfPresent(MongodbDO::getInstanceId, reqVO.getInstanceId())
                .likeIfPresent(MongodbDO::getInstanceName, reqVO.getInstanceName())
                .likeIfPresent(MongodbDO::getHost, reqVO.getHost())
                .likeIfPresent(MongodbDO::getClusterType, reqVO.getClusterType())
                .eqIfPresent(MongodbDO::getCpu, reqVO.getCpu())
                .eqIfPresent(MongodbDO::getMem, reqVO.getMem())
                .eqIfPresent(MongodbDO::getStorage, reqVO.getStorage())
                .eqIfPresent(MongodbDO::getLocation, reqVO.getLocation())
                .likeIfPresent(MongodbDO::getNotes, reqVO.getNotes())
                .eqIfPresent(MongodbDO::getOffline, reqVO.getOffline())
                .likeIfPresent(MongodbDO::getOu, reqVO.getOu())
                .likeIfPresent(MongodbDO::getTags, reqVO.getTags())
                .likeIfPresent(MongodbDO::getNodeInfo, reqVO.getNodeInfo())
                .likeIfPresent(MongodbDO::getExporterIp, reqVO.getExporterIp())
                .likeIfPresent(MongodbDO::getExporterPort, reqVO.getExporterPort())
                .eqIfPresent(MongodbDO::getMonitored, reqVO.getMonitored())
                .betweenIfPresent(MongodbDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MongodbDO::getId));
    }

}
