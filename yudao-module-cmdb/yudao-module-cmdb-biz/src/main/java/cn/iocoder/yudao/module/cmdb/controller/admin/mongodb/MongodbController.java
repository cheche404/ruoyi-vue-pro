package cn.iocoder.yudao.module.cmdb.controller.admin.mongodb;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mongodb.MongodbDO;
import cn.iocoder.yudao.module.cmdb.service.mongodb.MongodbService;
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

@Tag(name = "管理后台 - CMDB-MongoDB")
@RestController
@RequestMapping("/cmdb/mongodb")
@Validated
public class MongodbController {

    @Resource
    private MongodbService mongodbService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB-MongoDB")
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:create')")
    public CommonResult<Long> createMongodb(@Valid @RequestBody MongodbSaveReqVO createReqVO) {
        return success(mongodbService.createMongodb(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB-MongoDB")
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:update')")
    public CommonResult<Boolean> updateMongodb(@Valid @RequestBody MongodbSaveReqVO updateReqVO) {
        mongodbService.updateMongodb(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB-MongoDB")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:delete')")
    public CommonResult<Boolean> deleteMongodb(@RequestParam("id") Long id) {
        mongodbService.deleteMongodb(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB-MongoDB")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:query')")
    public CommonResult<MongodbRespVO> getMongodb(@RequestParam("id") Long id) {
        MongodbDO mongodb = mongodbService.getMongodb(id);
        return success(BeanUtils.toBean(mongodb, MongodbRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB-MongoDB分页")
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:query')")
    public CommonResult<PageResult<MongodbRespVO>> getMongodbPage(@Valid MongodbPageReqVO pageReqVO) {
        PageResult<MongodbDO> pageResult = mongodbService.getMongodbPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MongodbRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB-MongoDB Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMongodbExcel(@Valid MongodbPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MongodbDO> list = mongodbService.getMongodbPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB-MongoDB.xls", "数据", MongodbRespVO.class,
                        BeanUtils.toBean(list, MongodbRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入MongoDB模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<MongodbImportExcelVO> list = Arrays.asList(
          MongodbImportExcelVO.builder().cloudArea("aliyun").env("prod").center("dap")
            .team("middeware").user("middeware:mobile").promoter("")
            .instanceId("dds-uf6ad40e4397e574").instanceName("正式区MongoDB")
            .host("mongodbdap01.mongodb.rds.aliyuncs.com:3717,mongodbdap02.mongodb.rds.aliyuncs.com:3717")
            .clusterType("replicaset").cpu(null).mem(null).storage(2000)
            .location("N").notes("中间件").offline("N").ou("").tags("{}").nodeInfo("[]")
            .exporterIp("").exporterPort("").monitored("Y").build(),

          MongodbImportExcelVO.builder().cloudArea("azure").env("prod").center("athena")
            .team("athena").user("athena").promoter("")
            .instanceId("mongodb-altas-athena-001").instanceName("Athena")
            .host("athena-pl-0.i9fx4.mongodb.net:27017")
            .clusterType("replicaset").cpu(8).mem(32).storage(4096)
            .location("N").notes("智驱平台").offline("N").ou("").tags("{}")
            .nodeInfo("['athena-shard-00-00.i9fx4.mongodb.net:27017', 'athena-shard-00-01.i9fx4.mongodb.net:27017', 'athena-shard-00-02.i9fx4.mongodb.net:27017']")
            .exporterIp("").exporterPort("").monitored("Y").build(),

          MongodbImportExcelVO.builder().cloudArea("huawei").env("prod").center("ai")
            .team("ksc").user("ksc").promoter("")
            .instanceId("2af622c238fb49bb9290a5dc90858f98in02").instanceName("dds-kmo")
            .host("10.0.187.89:27017,10.0.82.96:27017,10.0.69.86:27017")
            .clusterType("replicaset").cpu(null).mem(null).storage(200)
            .location("N").notes("知识中台").offline("N").ou("").tags("{'area': 'Production', 'group': 'KSC'}")
            .nodeInfo("[{'id': '24d41caa1b324274bc8cb946ef3f3959no02', 'name': 'dds-kmo_replica_node_2', 'private_ip': '10.0.187.89', 'public_ip': ''}, {'id': '3765b755b2d248c0be255d127c112fa0no02', 'name': 'dds-kmo_replica_node_3', 'private_ip': '10.0.82.96', 'public_ip': ''}, {'id': 'ebf3538dded6468f9a67b25a1934b191no02', 'name': 'dds-kmo_replica_node_1', 'private_ip': '10.0.69.86', 'public_ip': ''}]")
            .exporterIp("").exporterPort("").monitored("Y").build()
        );
        // 输出
        ExcelUtils.write(response, "MongoDB导入模板.xls", "MongoDB列表", MongodbImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入CMDB MongoDB Excel")
    @Parameters({
      @Parameter(name = "file", description = "Excel 文件", required = true),
      @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('cmdb:mongodb:import')")
    public CommonResult<MongodbImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false")
                                                       Boolean updateSupport) throws Exception {
        List<MongodbImportExcelVO> list = ExcelUtils.read(file, MongodbImportExcelVO.class);
        return success(mongodbService.importMongodbList(list, updateSupport));
    }

}
