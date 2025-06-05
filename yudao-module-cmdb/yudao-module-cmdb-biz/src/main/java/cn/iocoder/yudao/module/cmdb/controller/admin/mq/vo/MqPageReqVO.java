package cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CMDB-MQ分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MqPageReqVO extends PageParam {

    @Schema(description = "云区域")
    private String cloudArea;

    @Schema(description = "环境")
    private String env;

    @Schema(description = "数据中心")
    private String center;

    @Schema(description = "团队")
    private String team;

    @Schema(description = "用户")
    private String user;

    @Schema(description = "推广者")
    private String promoter;

    @Schema(description = "域名")
    private String host;

    @Schema(description = "docker")
    private String docker;

    @Schema(description = "主机信息")
    private String nodes;

    @Schema(description = "集群名称", example = "李四")
    private String clusterName;

    @Schema(description = "自建")
    private String location;

    @Schema(description = "备注")
    private String notesInfo;

    @Schema(description = "是否离线")
    private String offline;

    @Schema(description = "exporter-ip")
    private String exporterIp;

    @Schema(description = "exporter端口")
    private String exporterPort;

    @Schema(description = "监控")
    private String monitored;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
