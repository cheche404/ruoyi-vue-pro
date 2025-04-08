package cn.iocoder.yudao.module.cmdb.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fudy
 * @date 2025/4/2
 */
@Schema(description = "管理后台 - CMDB模型精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelSimpleRespVO {

  @Schema(description = "模型编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
  private Long id;

  @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
  private String name;

  @Schema(description = "父模型 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
  private Long parentId;

}
