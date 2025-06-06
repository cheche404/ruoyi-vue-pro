package cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - CMDB对象属性 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AttributeRespVO {

    @Schema(description = "属性id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31401")
    @ExcelProperty("属性id")
    private Long id;

    @Schema(description = "属性名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("属性名称")
    private String name;

    @Schema(description = "属性编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("属性编码")
    private String code;

    @Schema(description = "所属对象id（关联cmdb_model表的id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2787")
    @ExcelProperty("所属对象id（关联cmdb_model表的id）")
    private Long modelId;

    @Schema(description = "排序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序号")
    private Integer sort;

    @Schema(description = "属性类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "input")
    @ExcelProperty("属性类型")
    private String attrType;

    @Schema(description = "属性校验规则（如正则表达式或长度限制）")
    @ExcelProperty("属性校验规则（如正则表达式或长度限制）")
    private String validationRule;

    @Schema(description = "是否必填（0否 1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否必填（0否 1是）")
    private Integer isRequired;

    @Schema(description = "是否能编辑（0否 1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否能编辑（0否 1是）")
    private Integer isEditable;

    @Schema(description = "是否单行展示（0否，一行展示两个属性；1是，单独一行展示）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否单行展示（0否，一行展示两个属性；1是，单独一行展示）")
    private Integer isSingleLine;

    @Schema(description = "属性描述", example = "随便")
    @ExcelProperty("属性描述")
    private String description;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "关联对象类型", example = "server")
    @ExcelProperty("关联对象类型")
    private String relationObjectType;

    @Schema(description = "对象模型 ID", example = "1001")
    @ExcelProperty("对象模型 ID")
    private Long objectModelId;

    @Schema(description = "关联字典", example = "2001")
    @ExcelProperty("关联字典")
    private String relationDictId;

}
