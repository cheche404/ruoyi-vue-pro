package cn.iocoder.yudao.module.cmdb.dal.dataobject.mongodb;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CMDB-MongoDB DO
 *
 * @author OPS管理员
 */
@TableName("cmdb_mongodb")
@KeySequence("cmdb_mongodb_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MongodbDO extends BaseDO {

    /**
     * MongoDB实例-ID
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
     * 实例ID
     */
    private String instanceId;
    /**
     * 实例名称
     */
    private String instanceName;
    /**
     * 域名
     */
    private String host;
    /**
     * 部署方式
     */
    private String clusterType;
    /**
     * CPU(核)
     */
    private Integer cpu;
    /**
     * 内存大小(GB)
     */
    private Integer mem;
    /**
     * 磁盘大小(GB)
     */
    private Integer storage;
    /**
     * 自建
     */
    private String location;
    /**
     * 备注
     */
    private String notes;
    /**
     * 离线
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
     * 主机信息
     */
    private String nodeInfo;
    /**
     * exporter-ip
     */
    private String exporterIp;
    /**
     * exporter端口
     */
    private String exporterPort;
    /**
     * 监控
     */
    private String monitored;

}
