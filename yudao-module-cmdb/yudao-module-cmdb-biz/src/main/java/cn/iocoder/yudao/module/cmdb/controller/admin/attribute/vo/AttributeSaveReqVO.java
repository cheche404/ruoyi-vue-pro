package cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - CMDB对象属性新增/修改 Request VO")
@Data
public class AttributeSaveReqVO {

    @Schema(description = "属性id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31401")
    private Long id;

    @Schema(description = "属性名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "属性名称不能为空")
    private String name;

    @Schema(description = "属性编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "属性编码不能为空")
    private String code;

    @Schema(description = "所属对象id（关联cmdb_model表的id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2787")
    @NotNull(message = "所属对象id（关联cmdb_model表的id）不能为空")
    private Long modelId;

    @Schema(description = "排序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序号不能为空")
    private Integer sort;

    @Schema(description = "属性类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "属性类型不能为空")
    private String attrType;

    @Schema(description = "属性校验规则（如正则表达式或长度限制）")
    private String validationRule;

    @Schema(description = "是否必填（0否 1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否必填（0否 1是）不能为空")
    private Boolean isRequired;

    @Schema(description = "是否能编辑（0否 1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否能编辑（0否 1是）不能为空")
    private Boolean isEditable;

    @Schema(description = "是否单行展示（0否，一行展示两个属性；1是，单独一行展示）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否单行展示（0否，一行展示两个属性；1是，单独一行展示）不能为空")
    private Boolean isSingleLine;

    @Schema(description = "属性描述", example = "随便")
    private String description;

    @Schema(description = "关联对象类型", example = "server")
    private String relationObjectType;

    @Schema(description = "对象模型 ID", example = "1001")
    private Long objectModelId;

    @Schema(description = "关联字典", example = "2001")
    private String relationDictId;

}
