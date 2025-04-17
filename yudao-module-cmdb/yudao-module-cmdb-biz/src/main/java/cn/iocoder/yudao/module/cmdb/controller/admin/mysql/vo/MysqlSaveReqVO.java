package cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - CMDB-MySQL新增/修改 Request VO")
@Data
public class MysqlSaveReqVO {

    @Schema(description = "MySQL实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6417")
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

    @Schema(description = "云实例ID", example = "8106")
    private String instanceId;

    @Schema(description = "云实例名称", example = "王五")
    private String instanceName;

    @Schema(description = "域名")
    private String host;

    @Schema(description = "实例部署方式", example = "1")
    private String clusterType;

    @Schema(description = "存储大小（单位：GB）")
    private Integer storage;

    @Schema(description = "location")
    private String location;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "是否离线")
    private String offline;

    @Schema(description = "组织单位")
    private String ou;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "监控exporterIP")
    private String exporterIp;

    @Schema(description = "监控exporter端口")
    private String exporterPort;

    @Schema(description = "监控")
    private String monitored;

}
