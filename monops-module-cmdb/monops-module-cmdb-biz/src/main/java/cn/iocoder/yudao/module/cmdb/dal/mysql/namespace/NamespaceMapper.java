package cn.iocoder.yudao.module.cmdb.dal.mysql.namespace;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo.NamespacePageReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.namespace.NamespaceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CMDB-namespace Mapper
 *
 * @author admin
 */
@Mapper
public interface NamespaceMapper extends BaseMapperX<NamespaceDO> {

    default NamespaceDO selectByCondition(String namespace, String cloudArea, String env) {
        return selectOne(NamespaceDO::getNamespace, namespace,
          NamespaceDO::getCloudArea, cloudArea, NamespaceDO::getEnv, env);
    }

    default PageResult<NamespaceDO> selectPage(NamespacePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NamespaceDO>()
                .eqIfPresent(NamespaceDO::getCloudArea, reqVO.getCloudArea())
                .eqIfPresent(NamespaceDO::getEnv, reqVO.getEnv())
                .eqIfPresent(NamespaceDO::getCenter, reqVO.getCenter())
                .eqIfPresent(NamespaceDO::getTeam, reqVO.getTeam())
                .eqIfPresent(NamespaceDO::getNamespace, reqVO.getNamespace())
                .likeIfPresent(NamespaceDO::getDeployment, reqVO.getDeployment())
                .eqIfPresent(NamespaceDO::getReplicas, reqVO.getReplicas())
                .betweenIfPresent(NamespaceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(NamespaceDO::getId));
    }

}
