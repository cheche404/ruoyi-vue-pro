package cn.iocoder.yudao.module.cmdb.service.mongodb;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.cmdb.controller.admin.mongodb.vo.*;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportRespVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mongodb.MongodbDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * CMDB-MongoDB Service 接口
 *
 * @author OPS管理员
 */
public interface MongodbService {

    /**
     * 创建CMDB-MongoDB
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMongodb(@Valid MongodbSaveReqVO createReqVO);

    /**
     * 更新CMDB-MongoDB
     *
     * @param updateReqVO 更新信息
     */
    void updateMongodb(@Valid MongodbSaveReqVO updateReqVO);

    /**
     * 删除CMDB-MongoDB
     *
     * @param id 编号
     */
    void deleteMongodb(Long id);

    /**
     * 获得CMDB-MongoDB
     *
     * @param id 编号
     * @return CMDB-MongoDB
     */
    MongodbDO getMongodb(Long id);

    /**
     * 获得CMDB-MongoDB分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB-MongoDB分页
     */
    PageResult<MongodbDO> getMongodbPage(MongodbPageReqVO pageReqVO);

    /**
     * 批量导入 CMDB MongoDB
     *
     * @param importMongodbs     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    MongodbImportRespVO importMongodbList(List<MongodbImportExcelVO> importMongodbs, boolean isUpdateSupport);


}
