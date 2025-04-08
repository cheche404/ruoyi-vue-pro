package cn.iocoder.yudao.module.cmdb.controller.admin.model;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.model.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.model.ModelDO;
import cn.iocoder.yudao.module.cmdb.service.model.ModelService;
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

@Tag(name = "管理后台 - CMDB模型")
@RestController
@RequestMapping("/cmdb/model")
@Validated
public class ModelController {

    @Resource
    private ModelService modelService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB模型")
    @PreAuthorize("@ss.hasPermission('cmdb:model:create')")
    public CommonResult<Long> createModel(@Valid @RequestBody ModelSaveReqVO createReqVO) {
        return success(modelService.createModel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB模型")
    @PreAuthorize("@ss.hasPermission('cmdb:model:update')")
    public CommonResult<Boolean> updateModel(@Valid @RequestBody ModelSaveReqVO updateReqVO) {
        modelService.updateModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB模型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:model:delete')")
    public CommonResult<Boolean> deleteModel(@RequestParam("id") Long id) {
        modelService.deleteModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:model:query')")
    public CommonResult<ModelRespVO> getModel(@RequestParam("id") Long id) {
        ModelDO model = modelService.getModel(id);
        return success(BeanUtils.toBean(model, ModelRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得CMDB模型列表")
    @PreAuthorize("@ss.hasPermission('cmdb:model:query')")
    public CommonResult<List<ModelRespVO>> getModelList(ModelListReqVO reqVO) {
        List<ModelDO> list = modelService.getModelList(reqVO);
        return success(BeanUtils.toBean(list, ModelRespVO.class));
    }

    @GetMapping(value = {"/list-all-simple", "/simple-list"})
    @Operation(summary = "获取CMDB模型信息列表", description = "只包含被开启的CMDB模型，主要用于前端的下拉选项")
    public CommonResult<List<ModelSimpleRespVO>> getSimpleDeptList() {
        List<ModelDO> list = modelService.getModelList(
          new ModelListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        return success(BeanUtils.toBean(list, ModelSimpleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB模型 Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:model:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportModelExcel(@Valid ModelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ModelDO> list = modelService.getModelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB模型.xls", "数据", ModelRespVO.class,
                        BeanUtils.toBean(list, ModelRespVO.class));
    }

}
