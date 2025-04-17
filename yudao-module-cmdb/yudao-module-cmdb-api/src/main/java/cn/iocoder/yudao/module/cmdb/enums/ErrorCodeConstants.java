package cn.iocoder.yudao.module.cmdb.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * cmdb 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== CMDB 对象模块 1_001_001_000 ==========
    ErrorCode MODEL_NOT_EXISTS = new ErrorCode(1_001_001_000, "对象模型不存在");

    // ========== CMDB 对象模块 1_002_001_000 ===========
    ErrorCode ATTRIBUTE_NOT_EXISTS = new ErrorCode(1_002_001_000, "对象属性不存在");

    // ========== CMDB host模块 1_003_001_000 ===========
    ErrorCode HOST_NOT_EXISTS = new ErrorCode(1_003_001_000, "主机不存在");
    ErrorCode HOST_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_003_001_001, "导入主机列表为空");
    ErrorCode HOST_INSTANCE_NAME_EXISTS = new ErrorCode(1_003_001_002, "主机 instanceName 已经存在");

    // ========== CMDB MySQL 模块 1_004_001_000 ===========
    ErrorCode MYSQL_NOT_EXISTS = new ErrorCode(1_004_001_000, "MySQL不存在");
    ErrorCode MYSQL_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_004_001_001, "导入MySQL列表为空");
    ErrorCode MYSQL_INSTANCE_NAME_EXISTS = new ErrorCode(1_004_001_002, "MySQL instanceName 已经存在");


}
