package cn.iocoder.yudao.module.monitor.controller.admin;

import cn.iocoder.yudao.module.monitor.dal.dataobject.alert.record.vo.AlertPayload;
import cn.iocoder.yudao.module.monitor.service.alert.record.AlertRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fudy
 * @date 2025/4/18
 */
@RestController
@RequestMapping(value = "/monitor/alert-record")
public class AlertRecordController {

  @Resource
  private AlertRecordService alertRecordService;

  // TODO 使用kafak做削峰
  @PostMapping("/receive")
  public String receiveAlert(@RequestBody AlertPayload payload) {
    alertRecordService.saveOrUpdateAlertRecord(payload);
    return "Alert processed";
  }

}
