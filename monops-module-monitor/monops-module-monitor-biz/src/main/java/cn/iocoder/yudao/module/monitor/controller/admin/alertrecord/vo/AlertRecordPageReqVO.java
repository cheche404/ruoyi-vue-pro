package cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 告警记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AlertRecordPageReqVO extends PageParam {

    @Schema(description = "告警名称", example = "赵六")
    private String alertName;

    @Schema(description = "告警开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] alertStartTime;

    @Schema(description = "告警结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] alertEndTime;

    @Schema(description = "告警来源")
    private String alertSource;

    @Schema(description = "环境信息")
    private String env;

    @Schema(description = "告警类型", example = "String")
    private String monitorType;

    @Schema(description = "告警状态", example = "1")
    private String status;

    @Schema(description = "命名空间")
    private String namespace;

    @Schema(description = "pod")
    private String pod;

    @Schema(description = "instance")
    private String instance;

    @Schema(description = "vhost")
    private String vhost;

    @Schema(description = "queue")
    private String queue;

    @Schema(description = "node")
    private String node;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "abi_origin_prometheus")
    private String abiOriginPrometheus;

    @Schema(description = "name", example = "芋艿")
    private String name;

    @Schema(description = "团队")
    private String team;

    @Schema(description = "summary")
    private String summary;

    @Schema(description = "description", example = "你猜")
    private String description;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
