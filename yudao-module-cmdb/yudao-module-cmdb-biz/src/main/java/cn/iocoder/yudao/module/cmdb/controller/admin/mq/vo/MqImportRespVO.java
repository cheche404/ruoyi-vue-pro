package cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - Mq导入 Response VO")
@Data
@Builder
public class MqImportRespVO {

    @Schema(description = "创建成功的Mq名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createClusterNames;

    @Schema(description = "更新成功的Mq名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateClusterNames;

    @Schema(description = "导入失败的Mq集合，key 为Mq名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureClusterNames;

}
