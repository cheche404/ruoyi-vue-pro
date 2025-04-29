package cn.iocoder.yudao.module.monitor.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Monitor 错误码枚举类
 *
 * monitor 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    ErrorCode ALERT_RECORD_NOT_EXISTS = new ErrorCode(1_002_001_000, "告警记录不存在");



}
