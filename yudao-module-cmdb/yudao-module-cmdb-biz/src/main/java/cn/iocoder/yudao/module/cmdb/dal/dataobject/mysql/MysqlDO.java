package cn.iocoder.yudao.module.cmdb.dal.dataobject.mysql;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * CMDB-MySQL DO
 *
 * @author 超级管理员
 */
@TableName("cmdb_mysql")
@KeySequence("cmdb_mysql_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MysqlDO extends BaseDO {

    /**
     * MySQL实例-ID
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
     * 用户
     */
    private String user;
    /**
     * 推广者
     */
    private String promoter;
    /**
     * 云实例ID
     */
    private String instanceId;
    /**
     * 云实例名称
     */
    private String instanceName;
    /**
     * 域名
     */
    private String host;
    /**
     * 实例部署方式
     */
    private String clusterType;
    /**
     * 存储大小（单位：GB）
     */
    private Integer storage;
    /**
     * location
     */
    private String location;
    /**
     * 备注
     */
    private String notes;
    /**
     * 是否离线
     */
    private String offline;
    /**
     * 组织单位
     */
    private String ou;
    /**
     * 标签
     */
    private String tags;
    /**
     * 监控exporterIP
     */
    private String exporterIp;
    /**
     * 监控exporter端口
     */
    private String exporterPort;
    /**
     * 监控
     */
    private String monitored;

}
