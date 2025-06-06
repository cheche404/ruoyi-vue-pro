package cn.iocoder.yudao.module.cmdb.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * cmdb 系统，使用 1-001-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== CMDB 对象模块 1_001_001_000 ==========
    ErrorCode MODEL_NOT_EXISTS = new ErrorCode(1_001_001_000, "对象模型不存在");

    // ========== CMDB 对象模块 1_001_002_000 ===========
    ErrorCode ATTRIBUTE_NOT_EXISTS = new ErrorCode(1_001_002_000, "对象属性不存在");

    // ========== CMDB host模块 1_001_003_000 ===========
    ErrorCode HOST_NOT_EXISTS = new ErrorCode(1_001_003_000, "主机不存在");
    ErrorCode HOST_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_001_003_001, "导入主机列表为空");
    ErrorCode HOST_INSTANCE_NAME_EXISTS = new ErrorCode(1_001_003_002, "主机 instanceName 已经存在");

    // ========== CMDB MySQL 模块 1_001_004_000 ===========
    ErrorCode MYSQL_NOT_EXISTS = new ErrorCode(1_001_004_000, "MySQL不存在");
    ErrorCode MYSQL_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_001_004_001, "导入MySQL列表为空");
    ErrorCode MYSQL_INSTANCE_NAME_EXISTS = new ErrorCode(1_001_004_002, "MySQL instanceName 已经存在");

    // ========== CMDB Redis 模块 1_001_005_000 ===========
    ErrorCode REDIS_NOT_EXISTS = new ErrorCode(1_001_005_000, "Redis不存在");
    ErrorCode REDIS_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_001_005_001, "导入Redis列表为空");
    ErrorCode REDIS_INSTANCE_NAME_EXISTS = new ErrorCode(1_001_005_002, "Redis instanceName 已经存在");

    // ========== CMDB MongoDB 模块 1_001_006_000 ===========
    ErrorCode MONGODB_NOT_EXISTS = new ErrorCode(1_001_006_000, "MongoDB不存在");
    ErrorCode MONGODB_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_001_006_001, "导入MongoDB列表为空");
    ErrorCode MONGODB_INSTANCE_NAME_EXISTS = new ErrorCode(1_001_006_002, "MongoDB instanceName 已经存在");

    // ========== CMDB Mq 模块 1_001_007_000 ===========
    ErrorCode MQ_NOT_EXISTS = new ErrorCode(1_001_007_000, "Mq不存在");
    ErrorCode MQ_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_001_007_001, "导入Mq列表为空");
    ErrorCode MQ_CLUSTER_NAME_EXISTS = new ErrorCode(1_001_007_002, "Mq clusterName 已经存在");

    // ========== CMDB Namespace 模块 1_001_008_000 ===========
    ErrorCode NAMESPACE_NOT_EXISTS = new ErrorCode(1_001_008_000, "Namespace不存在");
    ErrorCode NAMESPACE_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_001_008_001, "导入Namespace列表为空");
    ErrorCode NAMESPACE_CLUSTER_NAME_EXISTS = new ErrorCode(1_001_008_002, "Namespace 已经存在");
}
