package cn.iocoder.yudao.module.monitor.service.alert.record;

import cn.iocoder.yudao.module.monitor.dal.dataobject.alert.record.AlertRecordDO;
import cn.iocoder.yudao.module.monitor.dal.dataobject.alert.record.vo.AlertPayload;
import cn.iocoder.yudao.module.monitor.dal.mysql.alert.record.AlertRecordMapper;
import cn.iocoder.yudao.module.monitor.enums.AlertTypeInstant;
import cn.iocoder.yudao.module.monitor.utils.TimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fudy
 * @date 2024/12/10
 */
@Service
public class AlertRecordServiceImpl extends ServiceImpl<AlertRecordMapper, AlertRecordDO>
  implements AlertRecordService {

  private final AlertRecordMapper alertRecordMapper;

  public AlertRecordServiceImpl(AlertRecordMapper alertRecordMapper) {
    this.alertRecordMapper = alertRecordMapper;
  }

  @Override
  public void saveOrUpdateAlertRecord(AlertPayload payload) {
    // 遍历所有的告警
    for (AlertPayload.Alert alert : payload.getAlerts()) {
      // 判断告警的状态
      String alertStatus = alert.getStatus();
      if (StringUtils.isBlank(alertStatus)) {
        continue;
      }
      alertDeal(alert, alertStatus);
    }
  }


  /**
   * alert deal
   * @param alert alert
   */
  public void alertDeal(AlertPayload.Alert alert, String alertStatus) {
    // 存放判定唯一值的字段 map
    ConcurrentHashMap<String, String> queryMap = new ConcurrentHashMap<>();
    String monitorType = "";
    String alertSource = "";
    String monitorTypeValue = alert.getLabels().get(AlertTypeInstant.ATTR_MONITOR_TYPE);
    if (StringUtils.isNotBlank(monitorTypeValue)) {
      boolean isGrafana = monitorTypeValue.contains(AlertTypeInstant.MONITOR_TYPE_GRAFANA);
      if (isGrafana) {
        String[] parts = monitorTypeValue.split("-");
        if (parts.length > 1) {
          monitorType = parts[1];
        }
      } else {
        monitorType = monitorTypeValue;
      }
      alertSource = isGrafana ? AlertTypeInstant.ALERT_SOURCE_GRAFANA : AlertTypeInstant.ALERT_SOURCE_PROMETHEUS;
    }
    String alertName = alert.getLabels().get(AlertTypeInstant.ATTR_ALERTNAME);
    LocalDateTime startTime = TimeUtils.parseToLocalDateTime(alert.getStartsAt());
    LocalDateTime endTime = null;
    String summary = alert.getAnnotations().get("summary");
    String description = alert.getAnnotations().get("description");
    if (AlertTypeInstant.ALERT_STATUS_RESOLVED.equalsIgnoreCase(alertStatus)) {
      endTime = TimeUtils.parseToLocalDateTime(alert.getEndsAt());
    }

    // 判断环境 env
    String env = alert.getLabels().getOrDefault(AlertTypeInstant.ATTR_ORIGIN_PROMETHEUS,
      Arrays.stream(new String[] {AlertTypeInstant.HW_PROD, AlertTypeInstant.HW_TEST, AlertTypeInstant.AZ_PROD,
          AlertTypeInstant.AZ_TEST, AlertTypeInstant.ALI_PROD, AlertTypeInstant.ALI_TEST})
        .filter(summary::contains)
        .findFirst()
        .orElse(""));  // Default empty string if no match is found

    // 决定告警唯一值组合
    // 如果来源是 grafana , 根据告警的 alertname 作为唯一值; 否则根据不同的 monitor_type 做相应的唯一值处理
    if (AlertTypeInstant.ALERT_SOURCE_PROMETHEUS.equalsIgnoreCase(alertSource)) {
      switch (monitorType.toLowerCase()) {
        case AlertTypeInstant.MONITOR_TYPE_MQ:
          // 需要判断的标签
          String instance = alert.getLabels().get(AlertTypeInstant.ATTR_INSTANCE);
          String vhost = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_MQ_VHOST);
          String queue = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_MQ_QUEUE);
          queryMap.putIfAbsent(AlertTypeInstant.ATTR_INSTANCE, instance);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_MQ_VHOST, vhost);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_MQ_QUEUE, queue);
          break;
        case AlertTypeInstant.MONITOR_TYPE_K8S_NODE:
          String node = alert.getLabels().get(AlertTypeInstant.ATTR_NODE);
          queryMap.putIfAbsent(AlertTypeInstant.ATTR_NODE, node);
          break;
        case AlertTypeInstant.MONITOR_TYPE_POD:
          String namespace = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE);
          String pod = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_POD_POD);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE, namespace);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_POD_POD, pod);
          break;
        case AlertTypeInstant.MONITOR_TYPE_ABI:
          String origin_prometheus = alert.getLabels().get(AlertTypeInstant.ATTR_ORIGIN_PROMETHEUS);
          String url = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_ABI_URL);
          queryMap.putIfAbsent(AlertTypeInstant.ATTR_ORIGIN_PROMETHEUS, origin_prometheus);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_ABI_URL, url);
          break;
        case AlertTypeInstant.MONITOR_TYPE_HEALTH_CHECK:
          String name = alert.getLabels().get(AlertTypeInstant.ATTR_NAME);
          String team = alert.getLabels().get(AlertTypeInstant.ATTR_TEAM);
          String instanceCheck = alert.getLabels().get(AlertTypeInstant.ATTR_INSTANCE);
          queryMap.putIfAbsent(AlertTypeInstant.ATTR_NAME, name);
          queryMap.putIfAbsent(AlertTypeInstant.ATTR_TEAM, team);
          queryMap.putIfAbsent(AlertTypeInstant.ATTR_INSTANCE, instanceCheck);
          break;
        case AlertTypeInstant.MONITOR_TYPE_APP:
          String namespaceApp = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE);
          String podApp = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_POD_POD);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE, namespaceApp);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_POD_POD, podApp);
          break;
        case AlertTypeInstant.MONITOR_TYPE_JVM:
          String namespaceJvm = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE);
          String podJvm = alert.getLabels().get(AlertTypeInstant.MONITOR_TYPE_POD_POD);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE, namespaceJvm);
          queryMap.putIfAbsent(AlertTypeInstant.MONITOR_TYPE_POD_POD, podJvm);
          break;
        default:
          break;
      }
    }
    saveAlertRecord(alertSource, env, monitorType.toLowerCase(), alertName, alertStatus, startTime, endTime, queryMap, summary, description);
  }

  /**
   * 保存告警
   * @param monitorType monitorType
   * @param alertName alertName
   * @param queryMap queryMap
   */
  public void saveAlertRecord(String alertSource, String env, String monitorType,
                              String alertName, String alertStatus,
                              LocalDateTime startTime, LocalDateTime endTime,
                              ConcurrentHashMap<String, String> queryMap,
                              String summary, String description) {
    // 创建 QueryWrapper 对象
    QueryWrapper<AlertRecordDO> queryWrapper = new QueryWrapper<>();
    // 遍历 Map 中的每个键值对并拼接到 QueryWrapper 中
    for (Map.Entry<String, String> entry : queryMap.entrySet()) {
      // 将每个 key 和 value 添加到查询条件中
      queryWrapper.eq(entry.getKey(), entry.getValue());
    }
    queryWrapper.eq("alert_name", alertName)
      .isNull("alert_end_time")
      .orderByDesc("alert_start_time");

    AlertRecordDO alertRecordQuery = alertRecordMapper.selectOne(queryWrapper);

    // 可以根据业务需求将告警数据处理后插入数据库
    AlertRecordDO alertRecord = new AlertRecordDO();
    if (AlertTypeInstant.ALERT_STATUS_FIRING.equalsIgnoreCase(alertStatus)) {
      if (!Objects.isNull(alertRecordQuery)) {
        return;
      }

      if (AlertTypeInstant.ALERT_SOURCE_PROMETHEUS.equalsIgnoreCase(alertSource)) {
        switch (monitorType.toLowerCase()) {
          case AlertTypeInstant.MONITOR_TYPE_MQ:
            alertRecord.setInstance(queryMap.get(AlertTypeInstant.ATTR_INSTANCE));
            alertRecord.setVhost(queryMap.get(AlertTypeInstant.MONITOR_TYPE_MQ_VHOST));
            alertRecord.setQueue(queryMap.get(AlertTypeInstant.MONITOR_TYPE_MQ_QUEUE));
            break;
          case AlertTypeInstant.MONITOR_TYPE_K8S_NODE:
            alertRecord.setNode(queryMap.get(AlertTypeInstant.ATTR_NODE));
            break;
          case AlertTypeInstant.MONITOR_TYPE_POD:
            alertRecord.setNamespace(queryMap.get(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE));
            alertRecord.setPod(queryMap.get(AlertTypeInstant.MONITOR_TYPE_POD_POD));
            break;
          case AlertTypeInstant.MONITOR_TYPE_ABI:
            alertRecord.setAbiOriginPrometheus(queryMap.get(AlertTypeInstant.ATTR_ORIGIN_PROMETHEUS));
            alertRecord.setUrl(queryMap.get(AlertTypeInstant.MONITOR_TYPE_ABI_URL));
            break;
          case AlertTypeInstant.MONITOR_TYPE_HEALTH_CHECK:
            alertRecord.setName(queryMap.get(AlertTypeInstant.ATTR_NAME));
            alertRecord.setTeam(queryMap.get(AlertTypeInstant.ATTR_TEAM));
            alertRecord.setAbiOriginPrometheus(queryMap.get(AlertTypeInstant.ATTR_INSTANCE));
            break;
          case AlertTypeInstant.MONITOR_TYPE_APP:
            alertRecord.setNamespace(queryMap.get(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE));
            alertRecord.setPod(queryMap.get(AlertTypeInstant.MONITOR_TYPE_POD_POD));
            break;
          case AlertTypeInstant.MONITOR_TYPE_JVM:
            alertRecord.setNamespace(queryMap.get(AlertTypeInstant.MONITOR_TYPE_POD_NAMESPACE));
            alertRecord.setPod(queryMap.get(AlertTypeInstant.MONITOR_TYPE_POD_POD));
            break;
          default:
            break;
        }
      }
      alertRecord.setAlertSource(alertSource);
      alertRecord.setAlertName(alertName);
      alertRecord.setMonitorType(monitorType);
      alertRecord.setAlertStartTime(startTime);
      alertRecord.setStatus("告警中");
      alertRecord.setSummary(summary);
      alertRecord.setDescription(description);
      alertRecord.setEnv(env);
      alertRecordMapper.insert(alertRecord);
    } else if (AlertTypeInstant.ALERT_STATUS_RESOLVED.equalsIgnoreCase(alertStatus)) {
      if (Objects.isNull(alertRecordQuery)) {
        return;
      }
      LambdaUpdateWrapper<AlertRecordDO> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.eq(AlertRecordDO::getId, alertRecordQuery.getId());

      alertRecordQuery.setAlertEndTime(endTime);
      alertRecordQuery.setStatus("已恢复");
      alertRecordMapper.update(alertRecordQuery, updateWrapper);
    }
  }

}
