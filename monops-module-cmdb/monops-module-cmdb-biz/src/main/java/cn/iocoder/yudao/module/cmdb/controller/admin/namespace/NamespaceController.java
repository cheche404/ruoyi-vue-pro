package cn.iocoder.yudao.module.cmdb.controller.admin.namespace;

import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportRespVO;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.cmdb.controller.admin.namespace.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.namespace.NamespaceDO;
import cn.iocoder.yudao.module.cmdb.service.namespace.NamespaceService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - CMDB-namespace")
@RestController
@RequestMapping("/cmdb/namespace")
@Validated
public class NamespaceController {

    @Resource
    private NamespaceService namespaceService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB-namespace")
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:create')")
    public CommonResult<Long> createNamespace(@Valid @RequestBody NamespaceSaveReqVO createReqVO) {
        return success(namespaceService.createNamespace(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB-namespace")
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:update')")
    public CommonResult<Boolean> updateNamespace(@Valid @RequestBody NamespaceSaveReqVO updateReqVO) {
        namespaceService.updateNamespace(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB-namespace")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:delete')")
    public CommonResult<Boolean> deleteNamespace(@RequestParam("id") Long id) {
        namespaceService.deleteNamespace(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB-namespace")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:query')")
    public CommonResult<NamespaceRespVO> getNamespace(@RequestParam("id") Long id) {
        NamespaceDO namespace = namespaceService.getNamespace(id);
        return success(BeanUtils.toBean(namespace, NamespaceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB-namespace分页")
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:query')")
    public CommonResult<PageResult<NamespaceRespVO>> getNamespacePage(@Valid NamespacePageReqVO pageReqVO) {
        PageResult<NamespaceDO> pageResult = namespaceService.getNamespacePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, NamespaceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB-namespace Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportNamespaceExcel(@Valid NamespacePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<NamespaceDO> list = namespaceService.getNamespacePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB-namespace.xls", "数据", NamespaceRespVO.class,
                        BeanUtils.toBean(list, NamespaceRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入Namespace模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<NamespaceImportExcelVO> list = Arrays.asList(
          NamespaceImportExcelVO.builder().cloudArea("huawei").env("test").center("share").team("share").namespace("default")
            .deployment("").replicas(null).build(),

          NamespaceImportExcelVO.builder().cloudArea("azure").env("test").center("share").team("share").namespace("default")
            .deployment("").replicas(null).build(),

          NamespaceImportExcelVO.builder().cloudArea("aliyun").env("test").center("share").team("share").namespace("default")
            .deployment("").replicas(null).build()
        );
        // 输出
        ExcelUtils.write(response, "Namespace导入模板.xls", "Namespace列表", NamespaceImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入CMDB Namespace Excel")
    @Parameters({
      @Parameter(name = "file", description = "Excel 文件", required = true),
      @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('cmdb:namespace:import')")
    public CommonResult<NamespaceImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                    @RequestParam(value = "updateSupport", required = false, defaultValue = "false")
                                                    Boolean updateSupport) throws Exception {
        List<NamespaceImportExcelVO> list = ExcelUtils.read(file, NamespaceImportExcelVO.class);
        return success(namespaceService.importNamespaceList(list, updateSupport));
    }

}
