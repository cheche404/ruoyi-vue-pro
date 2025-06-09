package cn.iocoder.yudao.module.cmdb.service.attribute;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributePageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributeSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.attribute.AttributeDO;
import cn.iocoder.yudao.module.cmdb.dal.mysql.attribute.AttributeMapper;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * CMDB对象属性 Service 实现类
 *
 * @author 付东阳
 */
@Service
@Validated
public class AttributeServiceImpl implements AttributeService {

    @Resource
    private AttributeMapper attributeMapper;

    @Override
    public Long createAttribute(AttributeSaveReqVO createReqVO) {
        // 插入
        AttributeDO attribute = BeanUtils.toBean(createReqVO, AttributeDO.class);
        attributeMapper.insert(attribute);
        // 返回
        return attribute.getId();
    }

    @Override
    public void updateAttribute(AttributeSaveReqVO updateReqVO) {
        // 校验存在
        validateAttributeExists(updateReqVO.getId());
        // 更新
        AttributeDO updateObj = BeanUtils.toBean(updateReqVO, AttributeDO.class);
        attributeMapper.updateById(updateObj);
    }

    @Override
    public void deleteAttribute(Long id) {
        // 校验存在
        validateAttributeExists(id);
        // 删除
        attributeMapper.deleteById(id);
    }

    private void validateAttributeExists(Long id) {
        if (attributeMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.ATTRIBUTE_NOT_EXISTS);
        }
    }

    @Override
    public AttributeDO getAttribute(Long id) {
        return attributeMapper.selectById(id);
    }

    @Override
    public PageResult<AttributeDO> getAttributePage(AttributePageReqVO pageReqVO) {
        return attributeMapper.selectPage(pageReqVO);
    }

}
