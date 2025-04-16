package cn.iocoder.yudao.module.cmdb.dal.dataobject.host;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * CMDB主机 DO
 *
 * @author 付东阳
 */
@TableName("cmdb_host")
@KeySequence("cmdb_host_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostDO extends BaseDO {

    /**
     * 主机ID
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
     * 区域
     */
    private String area;
    /**
     * 推广者
     */
    private String promoter;
    /**
     * 内网IP
     */
    private String ipLan;
    /**
     * 外网IP
     */
    private String ipWan;
    /**
     * 云实例ID
     */
    private String instanceId;
    /**
     * 云实例名称
     */
    private String instanceName;
    /**
     * 是否K8S节点（Y:是 N:否）
     */
    private String k8sNode;
    /**
     * 是否离线（Y:是 N:否）
     */
    private String offline;
    /**
     * CPU核心数(单位: C)
     */
    private Integer cpu;
    /**
     * 内存大小（单位：GB）
     */
    private Integer mem;
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
     * 监控exporterIP
     */
    private String exporterIp;
    /**
     * 监控exporter端口
     */
    private String exporterPort;
    /**
     * 是否监控（Y:是 N:否）
     */
    private String monitored;

}