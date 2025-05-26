package cn.iocoder.yudao.module.cmdb.service.mysql;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportExcelVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlImportRespVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlPageReqVO;
import cn.iocoder.yudao.module.cmdb.controller.admin.mysql.vo.MysqlSaveReqVO;
import cn.iocoder.yudao.module.cmdb.dal.dataobject.mysql.MysqlDO;
import cn.iocoder.yudao.module.cmdb.dal.mysql.mysql.MysqlMapper;
import cn.iocoder.yudao.module.cmdb.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * CMDB-MySQL Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class MysqlServiceImpl implements MysqlService {

    @Resource
    private MysqlMapper mysqlMapper;

    @Override
    public Long createMysql(MysqlSaveReqVO createReqVO) {
        // 插入
        MysqlDO mysql = BeanUtils.toBean(createReqVO, MysqlDO.class);
        mysqlMapper.insert(mysql);
        // 返回
        return mysql.getId();
    }

    @Override
    public void updateMysql(MysqlSaveReqVO updateReqVO) {
        // 校验存在
        validateMysqlExists(updateReqVO.getId());
        // 更新
        MysqlDO updateObj = BeanUtils.toBean(updateReqVO, MysqlDO.class);
        mysqlMapper.updateById(updateObj);
    }

    @Override
    public void deleteMysql(Long id) {
        // 校验存在
        validateMysqlExists(id);
        // 删除
        mysqlMapper.deleteById(id);
    }

    private void validateMysqlExists(Long id) {
        if (mysqlMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.MYSQL_NOT_EXISTS);
        }
    }

    @Override
    public MysqlDO getMysql(Long id) {
        return mysqlMapper.selectById(id);
    }

    @Override
    public PageResult<MysqlDO> getMysqlPage(MysqlPageReqVO pageReqVO) {
        return mysqlMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public MysqlImportRespVO importMysqlList(List<MysqlImportExcelVO> importMysqls, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importMysqls)) {
            throw exception(ErrorCodeConstants.MYSQL_IMPORT_LIST_IS_EMPTY);
        }

        // 2. 遍历，逐个创建 or 更新
        MysqlImportRespVO respVO = MysqlImportRespVO.builder().createInstanceNames(new ArrayList<>())
          .updateInstanceNames(new ArrayList<>()).failureInstanceNames(new LinkedHashMap<>()).build();
        importMysqls.forEach(importMysql -> {
            // 2.1.1 判断如果不存在，在进行插入
            MysqlDO existMysql = mysqlMapper.selectByCondition(importMysql.getInstanceName(),
              importMysql.getCloudArea(), importMysql.getInstanceId());
            if (existMysql == null) {
                mysqlMapper.insert(BeanUtils.toBean(importMysql, MysqlDO.class));
                respVO.getCreateInstanceNames().add(importMysql.getInstanceName());
                return;
            }
            // 2.2.1 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureInstanceNames().put(importMysql.getInstanceName(),
                  ErrorCodeConstants.MYSQL_INSTANCE_NAME_EXISTS.getMsg());
                return;
            }
            MysqlDO updateMysql = BeanUtils.toBean(importMysql, MysqlDO.class);
            updateMysql.setId(existMysql.getId());
            mysqlMapper.updateById(updateMysql);
            respVO.getUpdateInstanceNames().add(importMysql.getInstanceName());
        });
        return respVO;
    }

}
