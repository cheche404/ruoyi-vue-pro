package cn.iocoder.yudao.module.cmdb.controller.admin.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - CMDB模型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ModelRespVO {

    @Schema(description = "模型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14956")
    @ExcelProperty("模型id")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("模型名称")
    private String name;

    @Schema(description = "模型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模型编码")
    private String code;

    @Schema(description = "父模型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17945")
    @ExcelProperty("父模型id")
    private Long parentId;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("显示顺序")
    private Integer sort;

    @Schema(description = "模型描述", example = "你说的对")
    @ExcelProperty("模型描述")
    private String description;

    @Schema(description = "图标")
    @ExcelProperty("图标")
    private String icon;

    @Schema(description = "模型状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("模型状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
