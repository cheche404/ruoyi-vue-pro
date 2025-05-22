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
                .eqIfPresent(MqDO::getCloudArea, reqVO.getCloudArea())
                .eqIfPresent(MqDO::getEnv, reqVO.getEnv())
                .eqIfPresent(MqDO::getCenter, reqVO.getCenter())
                .eqIfPresent(MqDO::getTeam, reqVO.getTeam())
                .eqIfPresent(MqDO::getUser, reqVO.getUser())
                .eqIfPresent(MqDO::getPromoter, reqVO.getPromoter())
                .eqIfPresent(MqDO::getHost, reqVO.getHost())
                .eqIfPresent(MqDO::getDocker, reqVO.getDocker())
                .eqIfPresent(MqDO::getNodes, reqVO.getNodes())
                .likeIfPresent(MqDO::getClusterName, reqVO.getClusterName())
                .eqIfPresent(MqDO::getLocation, reqVO.getLocation())
                .eqIfPresent(MqDO::getNotesInfo, reqVO.getNotesInfo())
                .eqIfPresent(MqDO::getExporterIp, reqVO.getExporterIp())
                .eqIfPresent(MqDO::getExporterPort, reqVO.getExporterPort())
                .eqIfPresent(MqDO::getMonitored, reqVO.getMonitored())
                .betweenIfPresent(MqDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MqDO::getId));
    }

}
