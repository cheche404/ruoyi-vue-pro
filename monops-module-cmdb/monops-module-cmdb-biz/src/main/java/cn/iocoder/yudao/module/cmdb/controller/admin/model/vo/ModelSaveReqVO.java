package cn.iocoder.yudao.module.cmdb.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - CMDB模型新增/修改 Request VO")
@Data
public class ModelSaveReqVO {

    @Schema(description = "模型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14956")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "模型名称不能为空")
    private String name;

    @Schema(description = "模型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模型编码不能为空")
    private String code;

    @Schema(description = "父模型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17945")
    @NotNull(message = "父模型id不能为空")
    private Long parentId;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "模型描述", example = "你说的对")
    private String description;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "模型状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "模型状态（0正常 1停用）不能为空")
    private Integer status;

}
