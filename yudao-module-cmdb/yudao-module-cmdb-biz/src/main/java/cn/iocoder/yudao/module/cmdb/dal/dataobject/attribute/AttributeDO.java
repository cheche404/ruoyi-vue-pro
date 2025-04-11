package cn.iocoder.yudao.module.cmdb.dal.dataobject.attribute;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * CMDB对象属性 DO
 *
 * @author 付东阳
 */
@TableName("cmdb_attribute")
@KeySequence("cmdb_attribute_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDO extends BaseDO {

    /**
     * 属性id
     */
    @TableId
    private Long id;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性编码
     */
    private String code;
    /**
     * 所属对象id（关联cmdb_model表的id）
     */
    private Long modelId;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 属性类型（0:字符串, 1:整数, 2:日期, 3:布尔值, 4:浮点数等）
     */
    private String attrType;
    /**
     * 属性校验规则（如正则表达式或长度限制）
     */
    private String validationRule;
    /**
     * 是否必填（0否 1是）
     */
    private Boolean isRequired;
    /**
     * 是否能编辑（0否 1是）
     */
    private Boolean isEditable;
    /**
     * 是否单行展示（0否，一行展示两个属性；1是，单独一行展示）
     */
    private Boolean isSingleLine;
    /**
     * 属性描述
     */
    private String description;
    /**
     * 关联对象类型
     */
    private String relationObjectType;
    /**
     * 对象模型 ID
     */
    private Long objectModelId;
    /**
     * 关联字典 ID
     */
    private Long relationDictId;

}
