package cn.iocoder.yudao.module.cmdb.dal.dataobject.redis;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CMDB-Redis DO
 *
 * @author 付东阳
 */
@TableName("cmdb_redis")
@KeySequence("cmdb_redis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisDO extends BaseDO {

    /**
     * Redis实例-ID
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
     * 域名只读
     */
    private String hostReadonly;
    /**
     * 部署方式
     */
    private String clusterType;
    /**
     * 端口
     */
    private String port;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 内存大小(GB)
     */
    private Integer mem;
    /**
     * 离线
     */
    private String offline;
    /**
     * location
     */
    private String location;
    /**
     * 备注
     */
    private String notes;
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
