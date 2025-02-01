package cn.iocoder.yudao.module.iot.enums.device;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * IoT 设备消息标识符枚举
 */
@Getter
@RequiredArgsConstructor
public enum IotDeviceMessageIdentifierEnum {

    PROPERTY_GET("get"), // 下行 TODO 芋艿：【讨论】貌似这个“上行”更合理？device 主动拉取配置。和 IotDevicePropertyGetReqDTO 一样的配置
    PROPERTY_SET("set"), // 下行
    PROPERTY_REPORT("report"), // 上行

    STATE_ONLINE("online"), // 上行
    STATE_OFFLINE("offline"), // 上行

    CONFIG_GET("get"), // 上行 TODO 芋艿：【讨论】暂时没有上行的场景
    CONFIG_SET("set"), // 下行

    SERVICE_INVOKE("${identifier}"), // 下行
    SERVICE_REPLY_SUFFIX("_reply"); // 芋艿：TODO 芋艿：【讨论】上行 or 下行

    /**
     * 标志符
     */
    private final String identifier;

}