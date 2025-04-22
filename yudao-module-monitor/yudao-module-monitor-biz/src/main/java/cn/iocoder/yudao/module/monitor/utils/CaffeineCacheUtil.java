package cn.iocoder.yudao.module.monitor.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author fudy
 * @date 2024/12/20
 */
public class CaffeineCacheUtil {

  // 静态缓存对象，用于存储活跃的告警 Key
  private static final Cache<String, Boolean> alarmCache = Caffeine.newBuilder()
    // 设置 永不过期
//    .expireAfterWrite(10, TimeUnit.MINUTES)
    .maximumSize(10000) // 最大存储 10000 条 Key，避免占用过多内存
    .build();

  // 私有化构造函数，防止实例化
  private CaffeineCacheUtil() {}

  /**
   * 添加告警 Key（触发告警）
   * @param key 告警的唯一标识
   */
  public static void addAlarm(String key) {
    alarmCache.put(key, true); // 将告警 Key 存入缓存，表示告警已触发
  }

  /**
   * 查询告警是否已激活
   * @param key 告警的唯一标识
   * @return 如果告警存在，则返回 true，否则返回 false
   */
  public static boolean isAlarmActive(String key) {
    return alarmCache.getIfPresent(key) != null; // 如果缓存中存在该 Key，则告警已激活
  }

  /**
   * 移除告警 Key（恢复告警）
   * @param key 告警的唯一标识
   */
  public static void removeAlarm(String key) {
    if (alarmCache.asMap().containsKey(key)) {
      alarmCache.invalidate(key); // 从缓存中删除告警 Key，表示该告警已恢复
    }
  }

}
