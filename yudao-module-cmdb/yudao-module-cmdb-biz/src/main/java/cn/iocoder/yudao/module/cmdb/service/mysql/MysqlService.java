package cn.iocoder.yudao.module.cmdb.service.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mysql.MysqlDO;

import javax.validation.Valid;
import java.util.List;

/**
 * CMDB-MySQL Service 接口
 *
 * @author 超级管理员
 */
public interface MysqlService {

    /**
     * 创建CMDB-MySQL
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMysql(@Valid MysqlSaveReqVO createReqVO);

    /**
     * 更新CMDB-MySQL
     *
     * @param updateReqVO 更新信息
     */
    void updateMysql(@Valid MysqlSaveReqVO updateReqVO);

    /**
     * 删除CMDB-MySQL
     *
     * @param id 编号
     */
    void deleteMysql(Long id);

    /**
     * 获得CMDB-MySQL
     *
     * @param id 编号
     * @return CMDB-MySQL
     */
    MysqlDO getMysql(Long id);

    /**
     * 获得CMDB-MySQL分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB-MySQL分页
     */
    PageResult<MysqlDO> getMysqlPage(MysqlPageReqVO pageReqVO);

    /**
     * 批量导入 CMDB MySQL
     *
     * @param importMysqls     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    MysqlImportRespVO importMysqlList(List<MysqlImportExcelVO> importMysqls, boolean isUpdateSupport);


}
