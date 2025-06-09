package cn.iocoder.yudao.module.cmdb.service.model;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.ModelListReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.ModelPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.ModelSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.model.ModelDO;

import javax.validation.Valid;
import java.util.List;

/**
 * CMDB模型 Service 接口
 *
 * @author 付东阳
 */
public interface ModelService {

    /**
     * 创建CMDB模型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createModel(@Valid ModelSaveReqVO createReqVO);

    /**
     * 更新CMDB模型
     *
     * @param updateReqVO 更新信息
     */
    void updateModel(@Valid ModelSaveReqVO updateReqVO);

    /**
     * 删除CMDB模型
     *
     * @param id 编号
     */
    void deleteModel(Long id);

    /**
     * 获得CMDB模型
     *
     * @param id 编号
     * @return CMDB模型
     */
    ModelDO getModel(Long id);

    /**
     * 获得CMDB模型分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB模型分页
     */
    PageResult<ModelDO> getModelPage(ModelPageReqVO pageReqVO);

    /**
     * 筛选CMDB model列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return CMDB model列表
     */
    List<ModelDO> getModelList(ModelListReqVO reqVO);

}
