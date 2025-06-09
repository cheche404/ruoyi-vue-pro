package cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 告警记录新增/修改 Request VO")
@Data
public class AlertRecordSaveReqVO {

    @Schema(description = "告警记录-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17338")
    private Long id;

    @Schema(description = "告警名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "告警名称不能为空")
    private String alertName;

    @Schema(description = "告警开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "告警开始时间不能为空")
    private LocalDateTime alertStartTime;

    @Schema(description = "告警结束时间")
    private LocalDateTime alertEndTime;

    @Schema(description = "告警来源")
    private String alertSource;

    @Schema(description = "环境信息")
    private String env;

    @Schema(description = "告警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "告警类型不能为空")
    private String monitorType;

    @Schema(description = "告警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "告警状态不能为空")
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

}