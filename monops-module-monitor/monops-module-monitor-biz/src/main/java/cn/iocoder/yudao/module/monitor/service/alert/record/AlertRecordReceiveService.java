package cn.iocoder.yudao.module.monitor.service.alert.record;


import cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.vo.AlertPayload;

/**
 * @author fudy
 * @date 2024/12/10
 */
public interface AlertRecordReceiveService {

  /**
   * saveOrUpdateAlertRecord
   *
   * @param payload payload
   */
  void saveOrUpdateAlertRecord(AlertPayload payload);

}
