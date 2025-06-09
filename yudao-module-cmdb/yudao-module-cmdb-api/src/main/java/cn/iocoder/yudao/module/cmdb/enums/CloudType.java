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
public enum CloudType implements ArrayValuable<String> {

  HUAWEI("huawei", "华为云", "simple-icons:huawei"),
  AZURE("azure", "微软云", "simple-icons:microsoftazure"),
  ALIYUN("aliyun", "阿里云", "simple-icons:alibabacloud");

  private final String cloud;
  private final String name;
  private final String icon;

  public static final String[] ARRAYS = Arrays.stream(values()).map(CloudType::getCloud).toArray(String[]::new);

  public static CloudType validateCloud(String cloud) {
    for (CloudType cloudType : CloudType.values()) {
      if (cloudType.getCloud().equals(cloud)) {
        return cloudType;
      }
    }
    throw new IllegalArgumentException("非法云类型： " + cloud);
  }

  @Override
  public String[] array() {
    return ARRAYS;
  }

}
