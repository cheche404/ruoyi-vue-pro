package cn.iocoder.yudao.module.cmdb.controller.admin.host;

import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.iocoder.yudao.module.cmdb.controller.admin.host.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.host.HostDO;
import cn.iocoder.yudao.module.cmdb.service.host.HostService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - CMDB主机")
@RestController
@RequestMapping("/cmdb/host")
@Validated
public class HostController {

    @Resource
    private HostService hostService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB主机")
    @PreAuthorize("@ss.hasPermission('cmdb:host:create')")
    public CommonResult<Long> createHost(@Valid @RequestBody HostSaveReqVO createReqVO) {
        return success(hostService.createHost(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB主机")
    @PreAuthorize("@ss.hasPermission('cmdb:host:update')")
    public CommonResult<Boolean> updateHost(@Valid @RequestBody HostSaveReqVO updateReqVO) {
        hostService.updateHost(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB主机")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:host:delete')")
    public CommonResult<Boolean> deleteHost(@RequestParam("id") Long id) {
        hostService.deleteHost(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB主机")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:host:query')")
    public CommonResult<HostRespVO> getHost(@RequestParam("id") Long id) {
        HostDO host = hostService.getHost(id);
        return success(BeanUtils.toBean(host, HostRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB主机分页")
    @PreAuthorize("@ss.hasPermission('cmdb:host:query')")
    public CommonResult<PageResult<HostRespVO>> getHostPage(@Valid HostPageReqVO pageReqVO) {
        PageResult<HostDO> pageResult = hostService.getHostPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HostRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB主机 Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:host:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportHostExcel(@Valid HostPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HostDO> list = hostService.getHostPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB主机.xls", "数据", HostRespVO.class,
                        BeanUtils.toBean(list, HostRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入主机模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<HostImportExcelVO> list = Arrays.asList(
          HostImportExcelVO.builder().cloudArea("aliyun").env("prod").center("dap")
            .team("devops").user("paas").area("").promoter("潘卫平panwp").ipLan("192.168.0.18")
            .ipWan("").instanceId("i-uf602dpe4ymdr0dbzclf").instanceName("prod-ops-192.168.0.18").k8sNode("Y")
            .offline("N").cpu(4).mem(16).notes("").ou("")
            .tags("{'area': 'Production', 'subgroup': 'OPS', 'group': 'DAP', 'ack': 'ack-prod'}")
            .exporterIp("").exporterPort("").monitored("Y").build(),
          HostImportExcelVO.builder().cloudArea("azure").env("prod").center("data")
            .team("athena-scrumbi").user("athena").area("").promoter("").ipLan("192.168.5.69")
            .ipWan("").instanceId("59585460-71d1-4c98-8a1e-6ff02493797b").instanceName("scrumbi-rmq-03new").k8sNode("N")
            .offline("N").cpu(2).mem(8).notes("").ou("")
            .tags("{'area': 'Production', 'group': 'Athena', 'owner': 'zhangyyk', 'subgroup': 'Scrumbi'}")
            .exporterIp("").exporterPort("").monitored("Y").build(),
          HostImportExcelVO.builder().cloudArea("huawei").env("test").center("isv")
            .team("isv").user("isv").area("isvcyiliukj").promoter("").ipLan("10.100.65.66")
            .ipWan("").instanceId("00dde91e-edbf-4042-9389-93ad282aa437").instanceName("test-athena-isv-8g-monthly-s5tre").k8sNode("Y")
            .offline("N").cpu(2).mem(8).notes("isv").ou("")
            .tags("{'area': 'Test', 'department': 'Athena', 'group': 'Athena', 'isv': 'True', 'subgroup': 'ISV'}")
            .exporterIp("").exporterPort("").monitored("Y").build()
        );
        // 输出
        ExcelUtils.write(response, "主机导入模板.xls", "主机列表", HostImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入CMDB主机 Excel")
    @Parameters({
      @Parameter(name = "file", description = "Excel 文件", required = true),
      @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('cmdb:host:import')")
    public CommonResult<HostImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false")
                                                      Boolean updateSupport) throws Exception {
        List<HostImportExcelVO> list = ExcelUtils.read(file, HostImportExcelVO.class);
        return success(hostService.importHostList(list, updateSupport));
    }

}
