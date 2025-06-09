package cn.iocoder.yudao.module.cmdb.dal.mysql.attribute;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributePageReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.attribute.AttributeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CMDB对象属性 Mapper
 *
 * @author 付东阳
 */
@Mapper
public interface AttributeMapper extends BaseMapperX<AttributeDO> {

    default PageResult<AttributeDO> selectPage(AttributePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AttributeDO>()
                .likeIfPresent(AttributeDO::getName, reqVO.getName())
                .eqIfPresent(AttributeDO::getCode, reqVO.getCode())
                .eqIfPresent(AttributeDO::getModelId, reqVO.getModelId())
                .eqIfPresent(AttributeDO::getSort, reqVO.getSort())
                .eqIfPresent(AttributeDO::getAttrType, reqVO.getAttrType())
                .eqIfPresent(AttributeDO::getRelationObjectType, reqVO.getRelationObjectType())
                .eqIfPresent(AttributeDO::getObjectModelId, reqVO.getObjectModelId())
                .eqIfPresent(AttributeDO::getRelationDictId, reqVO.getRelationDictId())
                .eqIfPresent(AttributeDO::getValidationRule, reqVO.getValidationRule())
                .eqIfPresent(AttributeDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(AttributeDO::getIsEditable, reqVO.getIsEditable())
                .eqIfPresent(AttributeDO::getIsSingleLine, reqVO.getIsSingleLine())
                .eqIfPresent(AttributeDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(AttributeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AttributeDO::getId));
    }

}
