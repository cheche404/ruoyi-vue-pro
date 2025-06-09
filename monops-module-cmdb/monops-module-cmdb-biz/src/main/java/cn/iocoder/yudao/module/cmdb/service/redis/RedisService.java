package cn.iocoder.yudao.module.cmdb.service.redis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.RedisImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.RedisImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.RedisPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.redis.vo.RedisSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.redis.RedisDO;

import javax.validation.Valid;
import java.util.List;

/**
 * CMDB-Redis Service 接口
 *
 * @author 付东阳
 */
public interface RedisService {

    /**
     * 创建CMDB-Redis
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRedis(@Valid RedisSaveReqVO createReqVO);

    /**
     * 更新CMDB-Redis
     *
     * @param updateReqVO 更新信息
     */
    void updateRedis(@Valid RedisSaveReqVO updateReqVO);

    /**
     * 删除CMDB-Redis
     *
     * @param id 编号
     */
    void deleteRedis(Long id);

    /**
     * 获得CMDB-Redis
     *
     * @param id 编号
     * @return CMDB-Redis
     */
    RedisDO getRedis(Long id);

    /**
     * 获得CMDB-Redis分页
     *
     * @param pageReqVO 分页查询
     * @return CMDB-Redis分页
     */
    PageResult<RedisDO> getRedisPage(RedisPageReqVO pageReqVO);


    /**
     * 批量导入 CMDB Redis
     *
     * @param importRedises     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    RedisImportRespVO importRedisList(List<RedisImportExcelVO> importRedises, boolean isUpdateSupport);

}
