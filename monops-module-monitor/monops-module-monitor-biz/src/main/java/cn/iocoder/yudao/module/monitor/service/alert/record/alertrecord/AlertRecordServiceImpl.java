package cn.iocoder.yudao.module.monitor.service.alert.record.alertrecord;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo.AlertRecordPageReqVO;
import cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo.AlertRecordSaveReqVO;
import cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.AlertRecordDO;
import cn.iocoder.yudao.module.monitor.dal.mysql.alertrecord.AlertRecordMapper;
import cn.iocoder.yudao.module.monitor.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 告警记录 Service 实现类
 *
 * @author 付东阳
 */
@Service
@Validated
public class AlertRecordServiceImpl implements AlertRecordService {

    @Resource
    private AlertRecordMapper alertRecordMapper;

    @Override
    public Long createAlertRecord(AlertRecordSaveReqVO createReqVO) {
        // 插入
        AlertRecordDO alertRecord = BeanUtils.toBean(createReqVO, AlertRecordDO.class);
        alertRecordMapper.insert(alertRecord);
        // 返回
        return alertRecord.getId();
    }

    @Override
    public void updateAlertRecord(AlertRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateAlertRecordExists(updateReqVO.getId());
        // 更新
        AlertRecordDO updateObj = BeanUtils.toBean(updateReqVO, AlertRecordDO.class);
        alertRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteAlertRecord(Long id) {
        // 校验存在
        validateAlertRecordExists(id);
        // 删除
        alertRecordMapper.deleteById(id);
    }

    private void validateAlertRecordExists(Long id) {
        if (alertRecordMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.ALERT_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public AlertRecordDO getAlertRecord(Long id) {
        return alertRecordMapper.selectById(id);
    }

    @Override
    public PageResult<AlertRecordDO> getAlertRecordPage(AlertRecordPageReqVO pageReqVO) {
        return alertRecordMapper.selectPage(pageReqVO);
    }

}
