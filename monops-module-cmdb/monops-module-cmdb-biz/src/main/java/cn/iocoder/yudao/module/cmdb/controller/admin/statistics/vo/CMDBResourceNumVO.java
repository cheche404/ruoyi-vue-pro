package cn.iocoder.yudao.module.cmdb.controller.admin.statistics.vo;

import lombok.Data;

import java.util.List;

/**
 * @author fudy
 * @date 2025/6/6
 */
@Data
public class CMDBResourceNumVO {

  /**
   * 资源类型
   */
  private String resourceType;

  private Long count;

  private String icon;

  private String menuName;

  private List<ResourceNumVO> resourceNumVOs;

  @Data
  public static class ResourceNumVO {
    private String cloudArea;
    private Long num;
    private String icon;
  }

}
