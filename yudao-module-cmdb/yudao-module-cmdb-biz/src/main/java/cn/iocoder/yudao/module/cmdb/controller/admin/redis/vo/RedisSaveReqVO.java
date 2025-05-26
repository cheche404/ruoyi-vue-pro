package cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - CMDB-Redis新增/修改 Request VO")
@Data
public class RedisSaveReqVO {

    @Schema(description = "Redis实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25986")
    private Long id;

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

    @Schema(description = "实例ID", example = "20746")
    private String instanceId;

    @Schema(description = "实例名称", example = "赵六")
    private String instanceName;

    @Schema(description = "域名")
    private String host;

    @Schema(description = "域名只读")
    private String hostReadonly;

    @Schema(description = "部署方式", example = "1")
    private String clusterType;

    @Schema(description = "端口")
    private String port;

    @Schema(description = "密码")
    private String passwd;

    @Schema(description = "内存大小(GB)")
    private Integer mem;

    @Schema(description = "离线")
    private String offline;

    @Schema(description = "自建")
    private String location;

    @Schema(description = "备注")
    private String notes;

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

}
