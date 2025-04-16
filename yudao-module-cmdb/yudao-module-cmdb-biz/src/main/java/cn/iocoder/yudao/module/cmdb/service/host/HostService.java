package cn.iocoder.yudao.module.cmdb.service.host;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.cmdb.controller.admin.host.vo.*;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.host.HostDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * CMDB主机 Service 接口
 *
 * @author 付东阳
 */
public interface HostService {

    /**
     * 创建CMDB主机
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createHost(@Valid HostSaveReqVO createReqVO);

    /**
     * 更新CMDB主机
     *
     * @param updateReqVO 更新信息
     */
    void updateHost(@Valid HostSaveReqVO updateReqVO);

    /**
     * 删除CMDB主机
     *
     * @param id 编号
     */
    void deleteHost(Long id);

    /**
     * 获得CMDB主机
     *
     * @param id 编号
     * @return CMDB主机
     */
    HostDO getHost(Long id);

    /**
     * 获得CMDB主机分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB主机分页
     */
    PageResult<HostDO> getHostPage(HostPageReqVO pageReqVO);

    /**
     * 批量导入 CMDB 主机
     *
     * @param importHosts     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    HostImportRespVO importHostList(List<HostImportExcelVO> importHosts, boolean isUpdateSupport);

}
