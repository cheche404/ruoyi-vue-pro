package cn.iocoder.yudao.module.cmdb.service.statistics;

import cn.iocoder.yudao.module.cmdb.controller.admin.statistics.vo.CMDBResourceNumVO;

import java.util.List;

/**
 * @author fudy
 * @date 2025/6/6
 */
public interface CMDBStatisticsService {

  /**
   * 获取各个资源的数量
   *
   * @return 资源数量
   */
  List<CMDBResourceNumVO> getResourceNum();

}
