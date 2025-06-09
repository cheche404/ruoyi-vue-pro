package cn.iocoder.yudao.module.cmdb.controller.admin.attribute;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributePageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributeRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.attribute.vo.AttributeSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.attribute.AttributeDO;
import cn.iocoder.yudao.module.cmdb.service.attribute.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - CMDB对象属性")
@RestController
@RequestMapping("/cmdb/attribute")
@Validated
public class AttributeController {

    @Resource
    private AttributeService attributeService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB对象属性")
    @PreAuthorize("@ss.hasPermission('cmdb:attribute:create')")
    public CommonResult<Long> createAttribute(@Valid @RequestBody AttributeSaveReqVO createReqVO) {
        return success(attributeService.createAttribute(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB对象属性")
    @PreAuthorize("@ss.hasPermission('cmdb:attribute:update')")
    public CommonResult<Boolean> updateAttribute(@Valid @RequestBody AttributeSaveReqVO updateReqVO) {
        attributeService.updateAttribute(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB对象属性")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:attribute:delete')")
    public CommonResult<Boolean> deleteAttribute(@RequestParam("id") Long id) {
        attributeService.deleteAttribute(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB对象属性")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:attribute:query')")
    public CommonResult<AttributeRespVO> getAttribute(@RequestParam("id") Long id) {
        AttributeDO attribute = attributeService.getAttribute(id);
        return success(BeanUtils.toBean(attribute, AttributeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB对象属性分页")
    @PreAuthorize("@ss.hasPermission('cmdb:attribute:query')")
    public CommonResult<PageResult<AttributeRespVO>> getAttributePage(@Valid AttributePageReqVO pageReqVO) {
        PageResult<AttributeDO> pageResult = attributeService.getAttributePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AttributeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB对象属性 Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:attribute:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAttributeExcel(@Valid AttributePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AttributeDO> list = attributeService.getAttributePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB对象属性.xls", "数据", AttributeRespVO.class,
                        BeanUtils.toBean(list, AttributeRespVO.class));
    }

}
