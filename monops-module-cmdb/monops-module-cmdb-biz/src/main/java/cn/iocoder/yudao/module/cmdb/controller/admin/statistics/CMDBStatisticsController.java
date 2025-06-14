package cn.iocoder.yudao.module.cmdb.controller.admin.statistics;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.statistics.vo.CMDBResourceNumVO;
import cn.iocoder.yudao.module.cmdb.service.statistics.CMDBStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @author fudy
 * @date 2025/6/6
 */
@Tag(name = "管理后台 - CMDB-statistics")
@RestController
@RequestMapping("/cmdb/statistics")
@Validated
public class CMDBStatisticsController {

  @Resource
  private CMDBStatisticsService cmdbStatisticsService;

  @GetMapping("/get-resource-num")
  @Operation(summary = "获得CMDB资源数量")
  public CommonResult<List<CMDBResourceNumVO>> getResourceNum() {
    List<CMDBResourceNumVO> list = cmdbStatisticsService.getResourceNum();
    return success(BeanUtils.toBean(list, CMDBResourceNumVO.class));
  }

}
