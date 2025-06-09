package cn.iocoder.yudao.module.cmdb.dal.dataobject.model;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CMDB模型 DO
 *
 * @author 付东阳
 */
@TableName("cmdb_model")
@KeySequence("cmdb_model_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDO extends BaseDO {

    /**
     * 模型id
     */
    @TableId
    private Long id;
    /**
     * 模型名称
     */
    private String name;
    /**
     * 模型编码
     */
    private String code;
    /**
     * 父模型id
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 模型描述
     */
    private String description;
    /**
     * 图标
     */
    private String icon;
    /**
     * 模型状态（0正常 1停用）
     */
    private Integer status;

}
