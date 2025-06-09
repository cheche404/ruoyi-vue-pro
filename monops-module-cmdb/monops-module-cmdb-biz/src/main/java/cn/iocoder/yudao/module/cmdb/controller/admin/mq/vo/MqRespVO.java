package cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - CMDB-MQ Response VO")
@Data
@ExcelIgnoreUnannotated
public class MqRespVO {

    @Schema(description = "MQ实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23748")
    @ExcelProperty("MQ实例-ID")
    private Long id;

    @Schema(description = "云区域")
    @ExcelProperty("云区域")
    private String cloudArea;

    @Schema(description = "环境")
    @ExcelProperty("环境")
    private String env;

    @Schema(description = "数据中心")
    @ExcelProperty("数据中心")
    private String center;

    @Schema(description = "团队")
    @ExcelProperty("团队")
    private String team;

    @Schema(description = "用户")
    @ExcelProperty("用户")
    private String user;

    @Schema(description = "推广者")
    @ExcelProperty("推广者")
    private String promoter;

    @Schema(description = "域名")
    @ExcelProperty("域名")
    private String host;

    @Schema(description = "docker")
    @ExcelProperty("docker")
    private String docker;

    @Schema(description = "主机信息")
    @ExcelProperty("主机信息")
    private String nodesInfo;

    @Schema(description = "集群名称", example = "李四")
    @ExcelProperty("集群名称")
    private String clusterName;

    @Schema(description = "自建")
    @ExcelProperty("自建")
    private String location;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String notesInfo;

    @Schema(description = "是否离线")
    @ExcelProperty("是否离线")
    private String offline;

    @Schema(description = "exporter-ip")
    @ExcelProperty("exporter-ip")
    private String exporterIp;

    @Schema(description = "exporter端口")
    @ExcelProperty("exporter端口")
    private String exporterPort;

    @Schema(description = "监控")
    @ExcelProperty("监控")
    private String monitored;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
