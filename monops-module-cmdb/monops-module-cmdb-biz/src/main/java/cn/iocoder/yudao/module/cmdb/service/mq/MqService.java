package cn.iocoder.yudao.module.cmdb.service.mq;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mq.vo.MqSaveReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportRespVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mq.MqDO;

import javax.validation.Valid;
import java.util.List;

/**
 * CMDB-MQ Service 接口
 *
 * @author admin
 */
public interface MqService {

    /**
     * 创建CMDB-MQ
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMq(@Valid MqSaveReqVO createReqVO);

    /**
     * 更新CMDB-MQ
     *
     * @param updateReqVO 更新信息
     */
    void updateMq(@Valid MqSaveReqVO updateReqVO);

    /**
     * 删除CMDB-MQ
     *
     * @param id 编号
     */
    void deleteMq(Long id);

    /**
     * 获得CMDB-MQ
     *
     * @param id 编号
     * @return CMDB-MQ
     */
    MqDO getMq(Long id);

    /**
     * 获得CMDB-MQ分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB-MQ分页
     */
    PageResult<MqDO> getMqPage(MqPageReqVO pageReqVO);

    /**
     * 批量导入 CMDB Mq
     *
     * @param importMqs     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    MqImportRespVO importMqList(List<MqImportExcelVO> importMqs, boolean isUpdateSupport);

}
