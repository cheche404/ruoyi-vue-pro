package cn.iocoder.yudao.module.monitor.dal.dataobject.alert.record;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * CMDB-Alert Record DO
 *
 * @author fudy
 */
@TableName("cmdb_alert_record")
@KeySequence("cmdb_alert_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertRecordDO extends BaseDO {

  /**
   * 告警记录-ID
   */
  @TableId
  private Long id;

  /**
   * 告警名称
   */
  private String alertName;

  /**
   * 告警开始时间
   */
  private LocalDateTime alertStartTime;

  /**
   * 告警结束时间
   */
  private LocalDateTime alertEndTime;

  /**
   * 告警来源
   */
  private String alertSource;

  /**
   * 环境信息
   */
  private String env;

  /**
   * 告警类型
   */
  private String monitorType;

  /**
   * 告警状态
   */
  private String status;

  /**
   * namespace
   */
  private String namespace;

  /**
   * pod
   */
  private String pod;

  /**
   * instance
   */
  private String instance;

  /**
   * vhost
   */
  private String vhost;

  /**
   * queue
   */
  private String queue;

  /**
   * node
   */
  private String node;

  /**
   * url
   */
  private String url;

  /**
   * abi_origin_prometheus
   */
  private String abiOriginPrometheus;

  /**
   * name
   */
  private String name;

  /**
   * 团队
   */
  private String team;

  /**
   * summary
   */
  private String summary;

  /**
   * description
   */
  private String description;

}
