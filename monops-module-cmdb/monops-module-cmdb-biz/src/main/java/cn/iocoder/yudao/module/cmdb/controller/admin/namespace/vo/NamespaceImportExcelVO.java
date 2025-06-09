package cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - CMDB-Mq Response VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class NamespaceImportExcelVO {

    @Schema(description = "namespace实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2904")
    @ExcelProperty("namespace实例-ID")
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

    @Schema(description = "命名空间")
    @ExcelProperty("命名空间")
    private String namespace;

    @Schema(description = "控制器")
    @ExcelProperty("控制器")
    private String deployment;

    @Schema(description = "副本数")
    @ExcelProperty("副本数")
    private Integer replicas;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
