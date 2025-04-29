package cn.iocoder.yudao.module.monitor.dal.mysql.alertrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.AlertRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo.*;

/**
 * 告警记录 Mapper
 *
 * @author 付东阳
 */
@Mapper
public interface AlertRecordMapper extends BaseMapperX<AlertRecordDO> {

    default PageResult<AlertRecordDO> selectPage(AlertRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AlertRecordDO>()
                .likeIfPresent(AlertRecordDO::getAlertName, reqVO.getAlertName())
                .betweenIfPresent(AlertRecordDO::getAlertStartTime, reqVO.getAlertStartTime())
                .betweenIfPresent(AlertRecordDO::getAlertEndTime, reqVO.getAlertEndTime())
                .likeIfPresent(AlertRecordDO::getAlertSource, reqVO.getAlertSource())
                .likeIfPresent(AlertRecordDO::getEnv, reqVO.getEnv())
                .likeIfPresent(AlertRecordDO::getMonitorType, reqVO.getMonitorType())
                .likeIfPresent(AlertRecordDO::getStatus, reqVO.getStatus())
                .likeIfPresent(AlertRecordDO::getNamespace, reqVO.getNamespace())
                .likeIfPresent(AlertRecordDO::getPod, reqVO.getPod())
                .likeIfPresent(AlertRecordDO::getInstance, reqVO.getInstance())
                .likeIfPresent(AlertRecordDO::getVhost, reqVO.getVhost())
                .likeIfPresent(AlertRecordDO::getQueue, reqVO.getQueue())
                .likeIfPresent(AlertRecordDO::getNode, reqVO.getNode())
                .likeIfPresent(AlertRecordDO::getUrl, reqVO.getUrl())
                .likeIfPresent(AlertRecordDO::getAbiOriginPrometheus, reqVO.getAbiOriginPrometheus())
                .likeIfPresent(AlertRecordDO::getName, reqVO.getName())
                .likeIfPresent(AlertRecordDO::getTeam, reqVO.getTeam())
                .likeIfPresent(AlertRecordDO::getSummary, reqVO.getSummary())
                .likeIfPresent(AlertRecordDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(AlertRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(AlertRecordDO::getStatus));
    }

}
