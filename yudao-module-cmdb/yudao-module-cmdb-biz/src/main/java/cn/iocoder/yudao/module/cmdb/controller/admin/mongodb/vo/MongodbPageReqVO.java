package cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CMDB-MongoDB分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MongodbPageReqVO extends PageParam {

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

    @Schema(description = "推广者")
    private String promoter;

    @Schema(description = "实例ID", example = "26467")
    private String instanceId;

    @Schema(description = "实例名称", example = "张三")
    private String instanceName;

    @Schema(description = "域名")
    private String host;

    @Schema(description = "部署方式", example = "2")
    private String clusterType;

    @Schema(description = "CPU(核)")
    private Integer cpu;

    @Schema(description = "内存大小(GB)")
    private Integer mem;

    @Schema(description = "磁盘大小(GB)")
    private Integer storage;

    @Schema(description = "自建")
    private String location;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "离线")
    private String offline;

    @Schema(description = "组织单位")
    private String ou;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "主机信息")
    private String nodeInfo;

    @Schema(description = "exporter-ip")
    private String exporterIp;

    @Schema(description = "exporter端口")
    private String exporterPort;

    @Schema(description = "监控")
    private String monitored;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}