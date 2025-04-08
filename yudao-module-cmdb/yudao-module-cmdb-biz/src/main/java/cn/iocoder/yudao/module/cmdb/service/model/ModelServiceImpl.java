package cn.iocoder.yudao.module.cmdb.service.model;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.ModelListReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.ModelPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.ModelSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.model.ModelDO;
import cn.iocoder.yudao.module.cmdb.dal.mysql.model.ModelMapper;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * CMDB模型 Service 实现类
 *
 * @author 付东阳
 */
@Service
@Validated
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;

    @Override
    public Long createModel(ModelSaveReqVO createReqVO) {
        // 插入
        ModelDO model = BeanUtils.toBean(createReqVO, ModelDO.class);
        modelMapper.insert(model);
        // 返回
        return model.getId();
    }

    @Override
    public void updateModel(ModelSaveReqVO updateReqVO) {
        // 校验存在
        validateModelExists(updateReqVO.getId());
        // 更新
        ModelDO updateObj = BeanUtils.toBean(updateReqVO, ModelDO.class);
        modelMapper.updateById(updateObj);
    }

    @Override
    public void deleteModel(Long id) {
        // 校验存在
        validateModelExists(id);
        // 删除
        modelMapper.deleteById(id);
    }

    private void validateModelExists(Long id) {
        if (modelMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.MODEL_NOT_EXISTS);
        }
    }

    @Override
    public ModelDO getModel(Long id) {
        return modelMapper.selectById(id);
    }

    @Override
    public PageResult<ModelDO> getModelPage(ModelPageReqVO pageReqVO) {
        return modelMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ModelDO> getModelList(ModelListReqVO reqVO) {
        List<ModelDO> list = modelMapper.selectList(reqVO);
        list.sort(Comparator.comparing(ModelDO::getSort));
        return list;
    }

}
