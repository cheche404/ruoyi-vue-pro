package cn.iocoder.yudao.module.cmdb.controller.admin.redis;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.redis.RedisDO;
import cn.iocoder.yudao.module.cmdb.service.redis.RedisService;
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

@Tag(name = "管理后台 - CMDB-Redis")
@RestController
@RequestMapping("/cmdb/redis")
@Validated
public class CMDBRedisController {

    @Resource
    private RedisService redisService;

    @PostMapping("/create")
    @Operation(summary = "创建CMDB-Redis")
    @PreAuthorize("@ss.hasPermission('cmdb:redis:create')")
    public CommonResult<Long> createRedis(@Valid @RequestBody RedisSaveReqVO createReqVO) {
        return success(redisService.createRedis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CMDB-Redis")
    @PreAuthorize("@ss.hasPermission('cmdb:redis:update')")
    public CommonResult<Boolean> updateRedis(@Valid @RequestBody RedisSaveReqVO updateReqVO) {
        redisService.updateRedis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CMDB-Redis")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmdb:redis:delete')")
    public CommonResult<Boolean> deleteRedis(@RequestParam("id") Long id) {
        redisService.deleteRedis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CMDB-Redis")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmdb:redis:query')")
    public CommonResult<RedisRespVO> getRedis(@RequestParam("id") Long id) {
        RedisDO redis = redisService.getRedis(id);
        return success(BeanUtils.toBean(redis, RedisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CMDB-Redis分页")
    @PreAuthorize("@ss.hasPermission('cmdb:redis:query')")
    public CommonResult<PageResult<RedisRespVO>> getRedisPage(@Valid RedisPageReqVO pageReqVO) {
        PageResult<RedisDO> pageResult = redisService.getRedisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RedisRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CMDB-Redis Excel")
    @PreAuthorize("@ss.hasPermission('cmdb:redis:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRedisExcel(@Valid RedisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RedisDO> list = redisService.getRedisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CMDB-Redis.xls", "数据", RedisRespVO.class,
                        BeanUtils.toBean(list, RedisRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入Redis模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<RedisImportExcelVO> list = Arrays.asList(
          RedisImportExcelVO.builder().cloudArea("aliyun").env("prod").center("industry")
            .team("industry").user("industry").promoter("耿明熔gengmr")
            .instanceId("r-uf6mvemw4kp2dip2pu").instanceName("正式區INDUSTRY_Redis")
            .host("industry.redis.rds.aliyuncs.com").hostReadonly("")
            .clusterType("cluster").port("6379").passwd("Y").mem(16)
            .offline("N").location("N").notes("").ou("").tags("{}").nodeInfo("192.168.19.16")
            .exporterIp("").exporterPort("").monitored("Y").build(),

          RedisImportExcelVO.builder().cloudArea("azure").env("prod").center("assc")
            .team("assc").user("assc").promoter("姜浩jianghaoa")
            .instanceId("").instanceName("assc")
            .host("assc.redis.cache.windows.net").hostReadonly("")
            .clusterType("ha").port("6379").passwd("Y").mem(1)
            .offline("N").location("N").notes("assc").ou("").tags("{'area': 'Production', 'group': 'Tiger'}")
            .nodeInfo("")
            .exporterIp("").exporterPort("").monitored("Y").build(),

          RedisImportExcelVO.builder().cloudArea("huawei").env("prod").center("data")
            .team("data").user("data").promoter("周导zhoudao")
            .instanceId("").instanceName("数据中台生产redis-10.0.63.139")
            .host("10.0.63.139").hostReadonly("")
            .clusterType("cluster").port("16379").passwd("Y").mem(122)
            .offline("N").location("Y").notes("数据中台生产redis-10.0.63.139").ou("").tags("{}").nodeInfo("")
            .exporterIp("").exporterPort("").monitored("Y").build()
        );
        // 输出
        ExcelUtils.write(response, "Redis导入模板.xls", "Redis列表", RedisImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入CMDB Redis Excel")
    @Parameters({
      @Parameter(name = "file", description = "Excel 文件", required = true),
      @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('cmdb:redis:import')")
    public CommonResult<RedisImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false")
                                                       Boolean updateSupport) throws Exception {
        List<RedisImportExcelVO> list = ExcelUtils.read(file, RedisImportExcelVO.class);
        return success(redisService.importRedisList(list, updateSupport));
    }

}
