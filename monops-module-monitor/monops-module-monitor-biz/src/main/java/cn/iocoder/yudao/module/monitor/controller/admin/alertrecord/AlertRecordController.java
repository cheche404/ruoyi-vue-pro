package cn.iocoder.yudao.module.monitor.controller.admin.alertrecord;

import cn.iocoder.yudao.module.monitor.service.alert.record.alertrecord.AlertRecordService;
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

import cn.iocoder.yudao.module.monitor.controller.admin.alertrecord.vo.*;
import cn.iocoder.yudao.module.monitor.dal.dataobject.alertrecord.AlertRecordDO;

@Tag(name = "管理后台 - 告警记录")
@RestController
@RequestMapping("/monitor/alert-record")
@Validated
public class AlertRecordController {

    @Resource
    private AlertRecordService alertRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建告警记录")
    @PreAuthorize("@ss.hasPermission('monitor:alert-record:create')")
    public CommonResult<Long> createAlertRecord(@Valid @RequestBody AlertRecordSaveReqVO createReqVO) {
        return success(alertRecordService.createAlertRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新告警记录")
    @PreAuthorize("@ss.hasPermission('monitor:alert-record:update')")
    public CommonResult<Boolean> updateAlertRecord(@Valid @RequestBody AlertRecordSaveReqVO updateReqVO) {
        alertRecordService.updateAlertRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除告警记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('monitor:alert-record:delete')")
    public CommonResult<Boolean> deleteAlertRecord(@RequestParam("id") Long id) {
        alertRecordService.deleteAlertRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得告警记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('monitor:alert-record:query')")
    public CommonResult<AlertRecordRespVO> getAlertRecord(@RequestParam("id") Long id) {
        AlertRecordDO alertRecord = alertRecordService.getAlertRecord(id);
        return success(BeanUtils.toBean(alertRecord, AlertRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得告警记录分页")
    @PreAuthorize("@ss.hasPermission('monitor:alert-record:query')")
    public CommonResult<PageResult<AlertRecordRespVO>> getAlertRecordPage(@Valid AlertRecordPageReqVO pageReqVO) {
        PageResult<AlertRecordDO> pageResult = alertRecordService.getAlertRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AlertRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出告警记录 Excel")
    @PreAuthorize("@ss.hasPermission('monitor:alert-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAlertRecordExcel(@Valid AlertRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AlertRecordDO> list = alertRecordService.getAlertRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "告警记录.xls", "数据", AlertRecordRespVO.class,
                        BeanUtils.toBean(list, AlertRecordRespVO.class));
    }

}
