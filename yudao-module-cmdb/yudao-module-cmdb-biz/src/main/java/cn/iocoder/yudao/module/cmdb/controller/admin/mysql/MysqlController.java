package cn.iocoder.yudao.module.cmdb.controller.admin.mysql;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.host.vo.HostImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mysql.MysqlDO;
import cn.iocoder.yudao.module.cmdb.service.mysql.MysqlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - CMDB-MySQL")
@RestController
@RequestMapping("/cmdb/mysql")
@Validated
public class MysqlController {

    @Resource
    private MysqlService mysqlService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB-MySQL")
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:create')")
    public CommonResult<Long> createMysql(@Valid @RequestBody MysqlSaveReqVO createReqVO) {
        return success(mysqlService.createMysql(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB-MySQL")
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:update')")
    public CommonResult<Boolean> updateMysql(@Valid @RequestBody MysqlSaveReqVO updateReqVO) {
        mysqlService.updateMysql(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB-MySQL")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:delete')")
    public CommonResult<Boolean> deleteMysql(@RequestParam("id") Long id) {
        mysqlService.deleteMysql(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB-MySQL")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:query')")
    public CommonResult<MysqlRespVO> getMysql(@RequestParam("id") Long id) {
        MysqlDO mysql = mysqlService.getMysql(id);
        return success(BeanUtils.toBean(mysql, MysqlRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB-MySQL分页")
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:query')")
    public CommonResult<PageResult<MysqlRespVO>> getMysqlPage(@Valid MysqlPageReqVO pageReqVO) {
        PageResult<MysqlDO> pageResult = mysqlService.getMysqlPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MysqlRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB-MySQL Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMysqlExcel(@Valid MysqlPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MysqlDO> list = mysqlService.getMysqlPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB-MySQL.xls", "数据", MysqlRespVO.class,
                        BeanUtils.toBean(list, MysqlRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入MySQL模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<MysqlImportExcelVO> list = Arrays.asList(
          MysqlImportExcelVO.builder().cloudArea("aliyun").env("prod").center("paas")
            .team("middeware").user("paas").promoter("")
            .instanceId("rm-uf6g7n4309rfi8a8n").instanceName("正式区dap MariaDB 10.3")
            .host("mariadb-dap.mariadb.rds.aliyuncs.com")
            .clusterType("ha").storage(100).location("N").notes("中间件").offline("N").ou("").tags("{}")
            .exporterIp("").exporterPort("").monitored("Y").build(),

          MysqlImportExcelVO.builder().cloudArea("azure").env("prod").center("report")
            .team("report").user("report").promoter("")
            .instanceId("").instanceName("athena-abi-flexible")
            .host("athena-abi-flexible.mysql.database.azure.com")
            .clusterType("ha").storage(30).location("N").notes("报表服务").offline("N").ou("").tags("{'area': 'Production', 'group': 'Athena'}")
            .exporterIp("").exporterPort("").monitored("Y").build(),

          MysqlImportExcelVO.builder().cloudArea("huawei").env("prod").center("tiger-bm")
            .team("tiger-bm").user("tiger-bm").promoter("")
            .instanceId("292948f0f662410f8011d859bae1a8d9in01").instanceName("tigerbm-posc")
            .host("292948f0f662410f8011d859bae1a8d9in01.internal.cn-east-3.mysql.rds.myhuaweicloud.com")
            .clusterType("ha").storage(60).location("N").notes("业务中台").offline("N").ou("").tags("{'department': 'TigerBM'}")
            .exporterIp("").exporterPort("").monitored("Y").build()
        );
        // 输出
        ExcelUtils.write(response, "MySQL导入模板.xls", "MySQL列表", MysqlImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入CMDB MySQL Excel")
    @Parameters({
      @Parameter(name = "file", description = "Excel 文件", required = true),
      @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('cmdb:mysql:import')")
    public CommonResult<MysqlImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false")
                                                      Boolean updateSupport) throws Exception {
        List<MysqlImportExcelVO> list = ExcelUtils.read(file, MysqlImportExcelVO.class);
        return success(mysqlService.importMysqlList(list, updateSupport));
    }

}
