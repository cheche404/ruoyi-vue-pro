package cn.iocoder.yudao.module.cmdb.dal.dataobject.namespace;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * CMDB-namespace DO
 *
 * @author admin
 */
@TableName("cmdb_namespace")
@KeySequence("cmdb_namespace_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NamespaceDO extends BaseDO {

    /**
     * namespace实例-ID
     */
    @TableId
    private Long id;
    /**
     * 云区域
     */
    private String cloudArea;
    /**
     * 环境
     */
    private String env;
    /**
     * 数据中心
     */
    private String center;
    /**
     * 团队
     */
    private String team;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 控制器
     */
    private String deployment;
    /**
     * 副本数
     */
    private Integer replicas;

}