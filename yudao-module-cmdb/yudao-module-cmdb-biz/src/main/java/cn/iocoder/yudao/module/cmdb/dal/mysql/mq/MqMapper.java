package cn.iocoder.yudao.module.cmdb.dal.mysql.mq;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqPageReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mq.MqDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CMDB-MQ Mapper
 *
 * @author admin
 */
@Mapper
public interface MqMapper extends BaseMapperX<MqDO> {

    default MqDO selectByCondition(String clusterName, String cloudArea, String host) {
      return selectOne(MqDO::getClusterName, clusterName,
        MqDO::getCloudArea, cloudArea, MqDO::getHost, host);
    }

    default PageResult<MqDO> selectPage(MqPageReqVO reqVO) {
      return selectPage(reqVO, new LambdaQueryWrapperX<MqDO>()
        .likeIfPresent(MqDO::getCloudArea, reqVO.getCloudArea())
        .likeIfPresent(MqDO::getEnv, reqVO.getEnv())
        .likeIfPresent(MqDO::getCenter, reqVO.getCenter())
        .likeIfPresent(MqDO::getTeam, reqVO.getTeam())
        .likeIfPresent(MqDO::getUser, reqVO.getUser())
        .likeIfPresent(MqDO::getPromoter, reqVO.getPromoter())
        .likeIfPresent(MqDO::getHost, reqVO.getHost())
        .likeIfPresent(MqDO::getDocker, reqVO.getDocker())
        .likeIfPresent(MqDO::getNodes, reqVO.getNodes())
        .likeIfPresent(MqDO::getClusterName, reqVO.getClusterName())
        .eqIfPresent(MqDO::getLocation, reqVO.getLocation())
        .likeIfPresent(MqDO::getNotesInfo, reqVO.getNotesInfo())
        .likeIfPresent(MqDO::getExporterIp, reqVO.getExporterIp())
        .likeIfPresent(MqDO::getExporterPort, reqVO.getExporterPort())
        .eqIfPresent(MqDO::getMonitored, reqVO.getMonitored())
        .betweenIfPresent(MqDO::getCreateTime, reqVO.getCreateTime())
        .orderByDesc(MqDO::getId));
    }

}
