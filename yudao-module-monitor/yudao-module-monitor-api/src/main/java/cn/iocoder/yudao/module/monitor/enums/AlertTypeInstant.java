package cn.iocoder.yudao.module.monitor.enums;

/**
 * @author fudy
 * @date 2024/12/10
 */
public interface AlertTypeInstant {

  /**
   * commond attribate
   */
  String ATTR_ALERTNAME = "alertname";
  String ATTR_INSTANCE = "instance";
  String ATTR_MONITOR_TYPE = "monitor_type";
  String ATTR_NODE = "node";
  String ATTR_ORIGIN_PROMETHEUS = "origin_prometheus";
  String ATTR_TEAM = "team";
  String ATTR_NAME = "name";

  /**
   * monitor type
   */
  String MONITOR_TYPE_GRAFANA = "grafana";

  String MONITOR_TYPE_MYSQL = "mysql";

  String MONITOR_TYPE_MONGODB = "mongodb";

  String MONITOR_TYPE_REIDS = "redis";

  String MONITOR_TYPE_NEO4J = "neo4j";

  // 主机
  String MONITOR_TYPE_HOST = "host";

  // k8s node
  String MONITOR_TYPE_K8S_NODE = "k8s_node";

  // POD
  String MONITOR_TYPE_POD = "pod";
  String MONITOR_TYPE_POD_NAMESPACE = "namespace";
  String MONITOR_TYPE_POD_POD = "pod";

  String MONITOR_TYPE_JVM = "jvm";

  String MONITOR_TYPE_MQ = "mq";
  String MONITOR_TYPE_MQ_VHOST = "vhost";
  String MONITOR_TYPE_MQ_QUEUE = "queue";

  String MONITOR_TYPE_ES = "es";

  String MONITOR_TYPE_APP = "app";

  String MONITOR_TYPE_KAFKA = "kafka";

  String MONITOR_TYPE_ABI = "abi";
  String MONITOR_TYPE_ABI_URL = "url";

  String MONITOR_TYPE_OTHER = "other";

  String MONITOR_TYPE_HEALTH_CHECK = "health_check";
  /**
   * alert status type
   */
  String ALERT_STATUS_FIRING = "firing";
  String ALERT_STATUS_RESOLVED = "resolved";
  String ALERT_STATUS_PENDING = "pending";

  /**
   * alert source
   */
  String ALERT_SOURCE_GRAFANA = "grafana";
  String ALERT_SOURCE_PROMETHEUS = "prometheus";

  /**
   * env
   */
  String HW_PROD = "华为云生产";
  String HW_TEST = "华为云测试";
  String AZ_PROD = "微软云生产";
  String AZ_TEST = "微软云测试";
  String ALI_PROD = "阿里云生产";
  String ALI_TEST = "阿里云测试";


}
