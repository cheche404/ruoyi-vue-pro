package cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author fudy
 * @date 2024/12/10
 */
@Data
public class AlertPayload {

  private String status;
  private List<Alert> alerts;

  @Data
  public static class Alert {
    private String status;
    private Map<String, String> labels;
//    // summary 和 description
    private Map<String, String> annotations;
    private String startsAt;
    private String endsAt;
  }

}
