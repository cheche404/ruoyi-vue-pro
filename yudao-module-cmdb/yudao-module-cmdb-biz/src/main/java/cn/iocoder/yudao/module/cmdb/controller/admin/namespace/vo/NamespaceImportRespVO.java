package cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - amespace导入 Response VO")
@Data
@Builder
public class NamespaceImportRespVO {

    @Schema(description = "创建成功的Namespace名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createNamespaceNames;

    @Schema(description = "更新成功的amespace名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateNamespaceNames;

    @Schema(description = "导入失败的amespace集合，key 为amespace名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureNamespaceNames;

}
