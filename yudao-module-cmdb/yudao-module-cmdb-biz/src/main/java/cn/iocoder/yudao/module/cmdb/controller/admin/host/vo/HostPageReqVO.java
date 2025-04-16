package cn.iocoder.yudao.module.cmdb.controller.admin.host.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CMDB主机分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HostPageReqVO extends PageParam {

    @Schema(description = "云区域")
    private String cloudArea;

    @Schema(description = "环境")
    private String env;

    @Schema(description = "数据中心")
    private String center;

    @Schema(description = "团队")
    private String team;

    @Schema(description = "用户")
    private String user;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "推广者")
    private String promoter;

    @Schema(description = "内网IP")
    private String ipLan;

    @Schema(description = "外网IP")
    private String ipWan;

    @Schema(description = "云实例ID", example = "29512")
    private String instanceId;

    @Schema(description = "云实例名称", example = "李四")
    private String instanceName;

    @Schema(description = "是否K8S节点（Y:是 N:否）")
    private String k8sNode;

    @Schema(description = "是否离线（Y:是 N:否）")
    private String offline;

    @Schema(description = "CPU核心数(单位: C)")
    private Integer cpu;

    @Schema(description = "内存大小（单位：GB）")
    private Integer mem;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "组织单位")
    private String ou;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "监控exporterIP")
    private String exporterIp;

    @Schema(description = "监控exporter端口")
    private String exporterPort;

    @Schema(description = "是否监控（Y:是 N:否）")
    private String monitored;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}