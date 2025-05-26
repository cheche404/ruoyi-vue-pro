package cn.iocoder.yudao.module.cmdb.controller.admin.mq;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mq.MqDO;
import cn.iocoder.yudao.module.cmdb.service.mq.MqService;
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

@Tag(name = "管理后台 - CMDB-MQ")
@RestController
@RequestMapping("/cmdb/mq")
@Validated
public class MqController {

    @Resource
    private MqService mqService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB-MQ")
    @PreAuthorize("@ss.hasPermission('cmdb:mq:create')")
    public CommonResult<Long> createMq(@Valid @RequestBody MqSaveReqVO createReqVO) {
        return success(mqService.createMq(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB-MQ")
    @PreAuthorize("@ss.hasPermission('cmdb:mq:update')")
    public CommonResult<Boolean> updateMq(@Valid @RequestBody MqSaveReqVO updateReqVO) {
        mqService.updateMq(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB-MQ")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:mq:delete')")
    public CommonResult<Boolean> deleteMq(@RequestParam("id") Long id) {
        mqService.deleteMq(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB-MQ")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:mq:query')")
    public CommonResult<MqRespVO> getMq(@RequestParam("id") Long id) {
        MqDO mq = mqService.getMq(id);
        return success(BeanUtils.toBean(mq, MqRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB-MQ分页")
    @PreAuthorize("@ss.hasPermission('cmdb:mq:query')")
    public CommonResult<PageResult<MqRespVO>> getMqPage(@Valid MqPageReqVO pageReqVO) {
        PageResult<MqDO> pageResult = mqService.getMqPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MqRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB-MQ Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:mq:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMqExcel(@Valid MqPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MqDO> list = mqService.getMqPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB-MQ.xls", "数据", MqRespVO.class,
                        BeanUtils.toBean(list, MqRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入Mq模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<MqImportExcelVO> list = Arrays.asList(
          MqImportExcelVO.builder().cloudArea("aliyun").env("paas").center("").team("").user("").promoter("")
            .host("mq-paas.digiwincloud.com.cn").docker("N").nodes("192.168.9.208").clusterName("阿里云paas-mq")
            .location("Y").notesInfo("阿里云paas-mq")
            .exporterIp("192.168.1.14").exporterPort("9421").monitored("Y").build(),

          MqImportExcelVO.builder().cloudArea("azure").env("prod").center("").team("").user("").promoter("")
            .host("mq.digiwincloud.com").docker("N").nodes("192.168.0.100").clusterName("微软云生产mq")
            .location("Y").notesInfo("微软云生产mq")
            .exporterIp("192.168.5.28").exporterPort("9420").monitored("Y").build(),

          MqImportExcelVO.builder().cloudArea("huawei").env("prod").center("").team("").user("").promoter("")
            .host("rmq-hw.digiwincloud.com.cn").docker("N").nodes("10.0.157.127,10.0.157.127,10.0.231.74").clusterName("athena生产rmq")
            .location("Y").notesInfo("athena生产rmq")
            .exporterIp("10.100.174.129").exporterPort("9422").monitored("Y").build()
        );
        // 输出
        ExcelUtils.write(response, "Mq导入模板.xls", "Mq列表", MqImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入CMDB Mq Excel")
    @Parameters({
      @Parameter(name = "file", description = "Excel 文件", required = true),
      @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('cmdb:mq:import')")
    public CommonResult<MqImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false")
                                                       Boolean updateSupport) throws Exception {
        List<MqImportExcelVO> list = ExcelUtils.read(file, MqImportExcelVO.class);
        return success(mqService.importMqList(list, updateSupport));
    }

}
