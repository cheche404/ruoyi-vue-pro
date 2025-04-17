package cn.iocoder.yudao.module.cmdb.dal.mysql.mysql;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.host.HostDO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mysql.MysqlDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.*;

/**
 * CMDB-MySQL Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MysqlMapper extends BaseMapperX<MysqlDO> {

    default MysqlDO selectByCondition(String instanceName, String cloudArea, String instanceId) {
        return selectOne(MysqlDO::getInstanceName, instanceName,
          MysqlDO::getCloudArea, cloudArea,
          MysqlDO::getInstanceId, instanceId);
    }

    default PageResult<MysqlDO> selectPage(MysqlPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MysqlDO>()
                .eqIfPresent(MysqlDO::getCloudArea, reqVO.getCloudArea())
                .eqIfPresent(MysqlDO::getEnv, reqVO.getEnv())
                .eqIfPresent(MysqlDO::getCenter, reqVO.getCenter())
                .eqIfPresent(MysqlDO::getTeam, reqVO.getTeam())
                .eqIfPresent(MysqlDO::getUser, reqVO.getUser())
                .likeIfPresent(MysqlDO::getPromoter, reqVO.getPromoter())
                .likeIfPresent(MysqlDO::getInstanceId, reqVO.getInstanceId())
                .likeIfPresent(MysqlDO::getInstanceName, reqVO.getInstanceName())
                .likeIfPresent(MysqlDO::getHost, reqVO.getHost())
                .eqIfPresent(MysqlDO::getClusterType, reqVO.getClusterType())
                .eqIfPresent(MysqlDO::getStorage, reqVO.getStorage())
                .eqIfPresent(MysqlDO::getLocation, reqVO.getLocation())
                .likeIfPresent(MysqlDO::getNotes, reqVO.getNotes())
                .eqIfPresent(MysqlDO::getOffline, reqVO.getOffline())
                .likeIfPresent(MysqlDO::getOu, reqVO.getOu())
                .likeIfPresent(MysqlDO::getTags, reqVO.getTags())
                .likeIfPresent(MysqlDO::getExporterIp, reqVO.getExporterIp())
                .likeIfPresent(MysqlDO::getExporterPort, reqVO.getExporterPort())
                .eqIfPresent(MysqlDO::getMonitored, reqVO.getMonitored())
                .betweenIfPresent(MysqlDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MysqlDO::getId));
    }

}
