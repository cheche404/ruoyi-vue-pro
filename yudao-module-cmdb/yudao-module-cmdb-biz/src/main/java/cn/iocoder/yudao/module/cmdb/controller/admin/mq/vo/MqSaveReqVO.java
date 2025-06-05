package cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - CMDB-MQ新增/修改 Request VO")
@Data
public class MqSaveReqVO {

    @Schema(description = "MQ实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23748")
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

    @Schema(description = "域名")
    private String host;

    @Schema(description = "docker")
    private String docker;

    @Schema(description = "主机信息")
    private String nodes;

    @Schema(description = "集群名称", example = "李四")
    private String clusterName;

    @Schema(description = "自建")
    private String location;

    @Schema(description = "备注")
    private String notesInfo;

    @Schema(description = "是否离线")
    private String offline;

    @Schema(description = "exporter-ip")
    private String exporterIp;

    @Schema(description = "exporter端口")
    private String exporterPort;

    @Schema(description = "监控")
    private String monitored;

}
