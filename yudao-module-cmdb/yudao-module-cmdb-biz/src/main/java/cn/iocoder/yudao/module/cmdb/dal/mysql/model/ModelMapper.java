package cn.iocoder.yudao.module.cmdb.dal.mysql.model;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.model.ModelDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.*;

/**
 * CMDB模型 Mapper
 *
 * @author 付东阳
 */
@Mapper
public interface ModelMapper extends BaseMapperX<ModelDO> {

    default List<ModelDO> selectList(ModelListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ModelDO>()
          .likeIfPresent(ModelDO::getName, reqVO.getName())
          .eqIfPresent(ModelDO::getStatus, reqVO.getStatus()));
    }


    default PageResult<ModelDO> selectPage(ModelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ModelDO>()
                .likeIfPresent(ModelDO::getName, reqVO.getName())
                .eqIfPresent(ModelDO::getCode, reqVO.getCode())
                .eqIfPresent(ModelDO::getParentId, reqVO.getParentId())
                .eqIfPresent(ModelDO::getSort, reqVO.getSort())
                .eqIfPresent(ModelDO::getDescription, reqVO.getDescription())
                .eqIfPresent(ModelDO::getIcon, reqVO.getIcon())
                .eqIfPresent(ModelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ModelDO::getId));
    }

}
