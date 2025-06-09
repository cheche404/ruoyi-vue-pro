package cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 告警记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AlertRecordRespVO {

    @Schema(description = "告警记录-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17338")
    @ExcelProperty("告警记录-ID")
    private Long id;

    @Schema(description = "告警名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("告警名称")
    private String alertName;

    @Schema(description = "告警开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("告警开始时间")
    private LocalDateTime alertStartTime;

    @Schema(description = "告警结束时间")
    @ExcelProperty("告警结束时间")
    private LocalDateTime alertEndTime;

    @Schema(description = "告警来源")
    @ExcelProperty("告警来源")
    private String alertSource;

    @Schema(description = "环境信息")
    @ExcelProperty("环境信息")
    private String env;

    @Schema(description = "告警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("告警类型")
    private String monitorType;

    @Schema(description = "告警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("告警状态")
    private String status;

    @Schema(description = "命名空间")
    @ExcelProperty("命名空间")
    private String namespace;

    @Schema(description = "pod")
    @ExcelProperty("pod")
    private String pod;

    @Schema(description = "instance")
    @ExcelProperty("instance")
    private String instance;

    @Schema(description = "vhost")
    @ExcelProperty("vhost")
    private String vhost;

    @Schema(description = "queue")
    @ExcelProperty("queue")
    private String queue;

    @Schema(description = "node")
    @ExcelProperty("node")
    private String node;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("url")
    private String url;

    @Schema(description = "abi_origin_prometheus")
    @ExcelProperty("abi_origin_prometheus")
    private String abiOriginPrometheus;

    @Schema(description = "name", example = "芋艿")
    @ExcelProperty("name")
    private String name;

    @Schema(description = "团队")
    @ExcelProperty("团队")
    private String team;

    @Schema(description = "summary")
    @ExcelProperty("summary")
    private String summary;

    @Schema(description = "description", example = "你猜")
    @ExcelProperty("description")
    private String description;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}