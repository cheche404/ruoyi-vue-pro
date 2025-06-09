package cn.iocoder.yudao.module.cmdb.service.namespace;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.namespace.NamespaceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * CMDB-namespace Service 接口
 *
 * @author admin
 */
public interface NamespaceService {

    /**
     * 创建CMDB-namespace
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createNamespace(@Valid NamespaceSaveReqVO createReqVO);

    /**
     * 更新CMDB-namespace
     *
     * @param updateReqVO 更新信息
     */
    void updateNamespace(@Valid NamespaceSaveReqVO updateReqVO);

    /**
     * 删除CMDB-namespace
     *
     * @param id 编号
     */
    void deleteNamespace(Long id);

    /**
     * 获得CMDB-namespace
     *
     * @param id 编号
     * @return CMDB-namespace
     */
    NamespaceDO getNamespace(Long id);

    /**
     * 获得CMDB-namespace分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB-namespace分页
     */
    PageResult<NamespaceDO> getNamespacePage(NamespacePageReqVO pageReqVO);

    /**
     * 批量导入 CMDB Namespace
     *
     * @param importNamespaces     导入Namespace列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    NamespaceImportRespVO importNamespaceList(List<NamespaceImportExcelVO> importNamespaces, boolean isUpdateSupport);

}
