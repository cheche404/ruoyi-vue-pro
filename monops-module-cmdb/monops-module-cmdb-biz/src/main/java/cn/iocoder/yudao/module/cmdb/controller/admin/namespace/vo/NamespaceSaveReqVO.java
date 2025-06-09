package cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - CMDB-namespace新增/修改 Request VO")
@Data
public class NamespaceSaveReqVO {

    @Schema(description = "namespace实例-ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2904")
    private Long id;

    @Schema(description = "云区域")
    private String cloudArea;

    @Schema(description = "环境")
    private String env;

    @Schema(description = "数据中心")
    private String center;

    @Schema(description = "团队")
    private String team;

    @Schema(description = "命名空间")
    private String namespace;

    @Schema(description = "控制器")
    private String deployment;

    @Schema(description = "副本数")
    private Integer replicas;

}