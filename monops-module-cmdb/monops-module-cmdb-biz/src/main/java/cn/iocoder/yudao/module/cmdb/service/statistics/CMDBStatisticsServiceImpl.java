package cn.iocoder.yudao.module.cmdb.service.statistics;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cmdb.controller.admin.statistics.vo.CMDBResourceNumVO;
import cn.iocoder.yudao.module.cmdb.enums.CloudType;
import cn.iocoder.yudao.module.cmdb.enums.ResourceType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fudy
 * @date 2025/6/6
 */
@Service
@Validated
public class CMDBStatisticsServiceImpl implements CMDBStatisticsService {

  @Resource
  private ApplicationContext context;

  @Override
  @SuppressWarnings("unchecked")
  public List<CMDBResourceNumVO> getResourceNum() {
    List<CMDBResourceNumVO> list = new ArrayList<>();
    for (ResourceType type : ResourceType.values()) {
      // 获取对应的 Mapper Bean
      BaseMapperX<?> mapper = (BaseMapperX<?>) context.getBean(type.getMapperBeanName());
      // 组装外层 VO
      CMDBResourceNumVO vo = new CMDBResourceNumVO();
      vo.setResourceType(type.getName());
      if (ResourceType.CONTAINER.getResourceType().equalsIgnoreCase(type.getResourceType())) {
        vo.setCount(mapper.selectCount());
      } else {
        vo.setCount(mapper.selectCount("offline", "N"));
      }
      vo.setIcon(type.getIcon());
      vo.setMenuName(type.getMenuName());

      // 组装内层云厂商数量 VO
      List<CMDBResourceNumVO.ResourceNumVO> innerVOS = new ArrayList<>();
      for (CloudType cloudType : CloudType.values()) {
        CMDBResourceNumVO.ResourceNumVO innerVO = new CMDBResourceNumVO.ResourceNumVO();
        String cloudName = cloudType.getCloud().toLowerCase();
        innerVO.setCloudArea(cloudType.getName());
        Long num = countByCloudArea((BaseMapperX<T>) mapper, cloudName, type);
        innerVO.setNum(num);
        innerVO.setIcon(cloudType.getIcon());
        innerVOS.add(innerVO);
      }
      vo.setResourceNumVOs(innerVOS);
      list.add(vo);
    }
    return list;
  }

  private Long countByCloudArea(BaseMapperX<T> mapper, String cloudArea, ResourceType type) {
    try {
      QueryWrapper<T> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("cloud_area", cloudArea);
      if (!ResourceType.CONTAINER.getResourceType().equalsIgnoreCase(type.getResourceType())) {
        queryWrapper.eq("offline", "N");
      }
      return mapper.selectCount(queryWrapper);
    } catch (Exception e) {
      return 0L; // 如果不支持 cloudArea 查询就返回 0
    }
  }


}
