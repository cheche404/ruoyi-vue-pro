package cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - Redis导入 Response VO")
@Data
@Builder
public class RedisImportRespVO {

    @Schema(description = "创建成功的Redis名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createInstanceNames;

    @Schema(description = "更新成功的Redis名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateInstanceNames;

    @Schema(description = "导入失败的Redis集合，key 为Redis名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureInstanceNames;

}
