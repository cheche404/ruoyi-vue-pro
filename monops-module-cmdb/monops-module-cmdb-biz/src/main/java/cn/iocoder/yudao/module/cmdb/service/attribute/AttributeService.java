package cn.iocoder.yudao.module.cmdb.service.attribute;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributePageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributeSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.attribute.AttributeDO;

import javax.validation.Valid;

/**
 * CMDB对象属性 Service 接口
 *
 * @author 付东阳
 */
public interface AttributeService {

    /**
     * 创建CMDB对象属性
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAttribute(@Valid AttributeSaveReqVO createReqVO);

    /**
     * 更新CMDB对象属性
     *
     * @param updateReqVO 更新信息
     */
    void updateAttribute(@Valid AttributeSaveReqVO updateReqVO);

    /**
     * 删除CMDB对象属性
     *
     * @param id 编号
     */
    void deleteAttribute(Long id);

    /**
     * 获得CMDB对象属性
     *
     * @param id 编号
     * @return CMDB对象属性
     */
    AttributeDO getAttribute(Long id);

    /**
     * 获得CMDB对象属性分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB对象属性分页
     */
    PageResult<AttributeDO> getAttributePage(AttributePageReqVO pageReqVO);

}
