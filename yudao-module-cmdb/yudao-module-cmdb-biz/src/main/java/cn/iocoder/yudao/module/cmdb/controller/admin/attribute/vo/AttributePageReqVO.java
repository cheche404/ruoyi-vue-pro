package cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CMDB对象属性分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AttributePageReqVO extends PageParam {

    @Schema(description = "属性名称", example = "李四")
    private String name;

    @Schema(description = "属性编码")
    private String code;

    @Schema(description = "所属对象id（关联cmdb_model表的id）", example = "2787")
    private Long modelId;

    @Schema(description = "排序号")
    private Integer sort;

    @Schema(description = "属性类型", example = "1")
    private String attrType;

    @Schema(description = "属性校验规则（如正则表达式或长度限制）")
    private String validationRule;

    @Schema(description = "是否必填（0否 1是）")
    private Boolean isRequired;

    @Schema(description = "是否能编辑（0否 1是）")
    private Boolean isEditable;

    @Schema(description = "是否单行展示（0否，一行展示两个属性；1是，单独一行展示）")
    private Boolean isSingleLine;

    @Schema(description = "属性描述", example = "属性描述")
    private String description;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "关联对象类型", example = "server")
    private String relationObjectType;

    @Schema(description = "对象模型 ID", example = "1001")
    private Long objectModelId;

    @Schema(description = "关联字典", example = "2001")
    private String relationDictId;

}
