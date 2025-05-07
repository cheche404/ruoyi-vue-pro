package cn.iocoder.yudao.module.monitor.controller.admin;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.vo.AlertPayload;
import cn.iocoder.yudao.module.monitor.service.alert.record.AlertRecordReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fudy
 * @date 2025/4/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/monitor/alert-record")
public class AlertRecordReceiveController {

  @Resource
  private AlertRecordReceiveService alertRecordReceiveService;

  // TODO 使用kafak做削峰
  @PostMapping("/receive")
  public String receiveAlert(@RequestBody AlertPayload payload) {
    String str = JsonUtils.toJsonString(payload);
    if (str.contains("rds-athena-cadence") || str.contains("health_check")) {
      System.out.println("------------接收到告警: " + JsonUtils.toJsonString(payload));
    }
    alertRecordReceiveService.saveOrUpdateAlertRecord(payload);
    return "Alert processed";
  }

}
