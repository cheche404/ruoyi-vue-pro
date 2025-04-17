package cn.iocoder.yudao.module.cmdb.dal.mysql.host;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.host.HostDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.cmdb.controller.admin.host.vo.*;

/**
 * CMDB主机 Mapper
 *
 * @author 付东阳
 */
@Mapper
public interface HostMapper extends BaseMapperX<HostDO> {

    default HostDO selectByCondition(String instanceName, String cloudArea, String instanceId) {
        return selectOne(HostDO::getInstanceName, instanceName,
          HostDO::getCloudArea, cloudArea,
          HostDO::getInstanceId, instanceId);
    }

    default PageResult<HostDO> selectPage(HostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HostDO>()
                .eqIfPresent(HostDO::getCloudArea, reqVO.getCloudArea())
                .eqIfPresent(HostDO::getEnv, reqVO.getEnv())
                .eqIfPresent(HostDO::getCenter, reqVO.getCenter())
                .eqIfPresent(HostDO::getTeam, reqVO.getTeam())
                .likeIfPresent(HostDO::getUser, reqVO.getUser())
                .likeIfPresent(HostDO::getArea, reqVO.getArea())
                .likeIfPresent(HostDO::getPromoter, reqVO.getPromoter())
                .likeIfPresent(HostDO::getIpLan, reqVO.getIpLan())
                .likeIfPresent(HostDO::getIpWan, reqVO.getIpWan())
                .likeIfPresent(HostDO::getInstanceId, reqVO.getInstanceId())
                .likeIfPresent(HostDO::getInstanceName, reqVO.getInstanceName())
                .eqIfPresent(HostDO::getK8sNode, reqVO.getK8sNode())
                .eqIfPresent(HostDO::getOffline, reqVO.getOffline())
                .eqIfPresent(HostDO::getCpu, reqVO.getCpu())
                .eqIfPresent(HostDO::getMem, reqVO.getMem())
                .likeIfPresent(HostDO::getNotes, reqVO.getNotes())
                .likeIfPresent(HostDO::getOu, reqVO.getOu())
                .likeIfPresent(HostDO::getTags, reqVO.getTags())
                .likeIfPresent(HostDO::getExporterIp, reqVO.getExporterIp())
                .likeIfPresent(HostDO::getExporterPort, reqVO.getExporterPort())
                .eqIfPresent(HostDO::getMonitored, reqVO.getMonitored())
                .betweenIfPresent(HostDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HostDO::getId));
    }

}
