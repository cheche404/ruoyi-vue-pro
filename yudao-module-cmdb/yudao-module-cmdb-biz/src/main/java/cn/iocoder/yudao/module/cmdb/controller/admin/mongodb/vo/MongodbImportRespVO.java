package cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - MongoDB导入 Response VO")
@Data
@Builder
public class MongodbImportRespVO {

    @Schema(description = "创建成功的MongoDB名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createInstanceNames;

    @Schema(description = "更新成功的MongoDB名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateInstanceNames;

    @Schema(description = "导入失败的MongoDB集合，key 为MongoDB名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureInstanceNames;

}
