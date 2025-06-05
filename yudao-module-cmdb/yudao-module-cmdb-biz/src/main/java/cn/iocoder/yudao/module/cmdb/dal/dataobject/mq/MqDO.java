package cn.iocoder.yudao.module.cmdb.dal.dataobject.mq;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CMDB-MQ DO
 *
 * @author admin
 */
@TableName("cmdb_rabbitmq")
@KeySequence("cmdb_rabbitmq_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqDO extends BaseDO {

    /**
     * MQ实例-ID
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
     * 域名
     */
    private String host;
    /**
     * docker
     */
    private String docker;
    /**
     * 主机信息
     */
    private String nodes;
    /**
     * 集群名称
     */
    private String clusterName;
    /**
     * 自建
     */
    private String location;
    /**
     * 备注
     */
    private String notesInfo;
    /**
     * 离线
     */
    private String offline;
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
