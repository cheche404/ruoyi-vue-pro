package cn.iocoder.yudao.module.cmdb.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author fudy
 * @date 2025/4/3
 */
@Schema(description = "管理后台 - CMDB model列表 Request VO")
@Data
public class ModelListReqVO {

  @Schema(description = "模型名称，模糊匹配", example = "芋道")
  private String name;

  @Schema(description = "展示状态，参见 CommonStatusEnum 枚举类", example = "1")
  private Integer status;

}
