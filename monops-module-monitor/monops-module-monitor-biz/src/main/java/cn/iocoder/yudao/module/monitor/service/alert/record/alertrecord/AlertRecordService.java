package cn.iocoder.yudao.module.monitor.service.alert.record.alertrecord;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo.AlertRecordPageReqVO;
import cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo.AlertRecordSaveReqVO;
import cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.AlertRecordDO;

import javax.validation.Valid;

/**
 * 告警记录 Service 接口
 *
 * @author 付东阳
 */
public interface AlertRecordService {

    /**
     * 创建告警记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAlertRecord(@Valid AlertRecordSaveReqVO createReqVO);

    /**
     * 更新告警记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAlertRecord(@Valid AlertRecordSaveReqVO updateReqVO);

    /**
     * 删除告警记录
     *
     * @param id 编号
     */
    void deleteAlertRecord(Long id);

    /**
     * 获得告警记录
     *
     * @param id 编号
     * @return 告警记录
     */
    AlertRecordDO getAlertRecord(Long id);

    /**
     * 获得告警记录分页
     *
     * @param pageReqVO 分页查询
     * @return 告警记录分页
     */
    PageResult<AlertRecordDO> getAlertRecordPage(AlertRecordPageReqVO pageReqVO);

}
