package cn.iocoder.yudao.module.cmdb.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author fudy
 * @date 2025/6/6
 */
@Getter
@AllArgsConstructor
public enum ResourceType implements ArrayValuable<String> {

  MYSQL("mysql", "MySQL", "mysqlMapper", "logos:mysql", "CMDB-MySQL"),
  REDIS("redis", "Redis", "redisMapper", "logos:redis", "CMDB-Redis"),
  MONGODB("mongodb", "Mongodb", "mongodbMapper", "logos:mongodb-icon", "CMDB-MongoDB"),
  MQ("mq", "MQ", "mqMapper", "logos:rabbitmq-icon", "CMDB-MQ"),
  HOST("host", "主机", "hostMapper","mdi:server", "CMDB-Host"),
  CONTAINER("container", "容器", "namespaceMapper", "simple-icons:kubernetes", "CMDB-Namespace"),;

  private final String resourceType;
  private final String name;
  private final String mapperBeanName;
  private final String icon;
  private final String menuName;

  public static final String[] ARRAYS = Arrays.stream(values()).map(ResourceType::getResourceType).toArray(String[]::new);

  public static ResourceType validateType(String type) {
    for (ResourceType resourceType : ResourceType.values()) {
      if (resourceType.getResourceType().equals(type)) {
        return resourceType;
      }
    }
    throw new IllegalArgumentException("非法资源类型： " + type);
  }

  @Override
  public String[] array() {
    return ARRAYS;
  }
}
