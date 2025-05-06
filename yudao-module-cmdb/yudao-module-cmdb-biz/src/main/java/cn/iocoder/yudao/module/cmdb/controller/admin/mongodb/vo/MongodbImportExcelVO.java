package cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - CMDB-MongoDB Excel VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class MongodbImportExcelVO {

    @Schema(description = "MongoDB实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2361")
    @ExcelProperty("MongoDB实例-ID")
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

    @Schema(description = "实例ID", example = "26467")
    @ExcelProperty("实例ID")
    private String instanceId;

    @Schema(description = "实例名称", example = "张三")
    @ExcelProperty("实例名称")
    private String instanceName;

    @Schema(description = "域名")
    @ExcelProperty("域名")
    private String host;

    @Schema(description = "部署方式", example = "2")
    @ExcelProperty("部署方式")
    private String clusterType;

    @Schema(description = "CPU(核)")
    @ExcelProperty("CPU(核)")
    private Integer cpu;

    @Schema(description = "内存大小(GB)")
    @ExcelProperty("内存大小(GB)")
    private Integer mem;

    @Schema(description = "磁盘大小(GB)")
    @ExcelProperty("磁盘大小(GB)")
    private Integer storage;

    @Schema(description = "自建")
    @ExcelProperty("自建")
    private String location;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String notes;

    @Schema(description = "离线")
    @ExcelProperty("离线")
    private String offline;

    @Schema(description = "组织单位")
    @ExcelProperty("组织单位")
    private String ou;

    @Schema(description = "标签")
    @ExcelProperty("标签")
    private String tags;

    @Schema(description = "主机信息")
    @ExcelProperty("主机信息")
    private String nodeInfo;

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
