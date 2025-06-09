package cn.iocoder.yudao.module.monitor.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author fudy
 * @date 2024/12/2
 */
public class TimeUtils {

  /**
   * 将带有时区偏移的字符串解析为 LocalDateTime。
   * @param input 带时区偏移的日期时间字符串
   * @return 解析后的 LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(String input) {
    // 去掉时区偏移部分，假设格式为 "yyyy-MM-dd'T'HH:mm:ss Z"
    String cleanedInput = input.split(" ")[0]; // 取 "2024-12-10T17:56:30"

    // 定义 LocalDateTime 格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    return LocalDateTime.parse(cleanedInput, formatter);
  }

  // 将带时区偏移的字符串转换为 LocalDateTime
  public static LocalDateTime parseToLocalDateTime(String input) {
    // 定义带时区偏移的格式
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    try {
      // 先解析为 OffsetDateTime
      OffsetDateTime offsetDateTime = OffsetDateTime.parse(input, formatter);
      // 转换为 LocalDateTime
      return offsetDateTime.toLocalDateTime();
    } catch (Exception e) {
      System.err.println("Error parsing date time: " + e.getMessage());
      return null;
    }
  }

  /**
   * 判断输入的时间字符串是否包含 "0001-01-01T00:00:00Z"
   * @param input 时间字符串
   * @return 如果时间字符串包含 "0001-01-01T00:00:00Z" 则返回 true，否则返回 false
   */
  public static boolean containsDefaultDateTime(String input) {
    // 清理字符串：去掉空格并统一大小写
    String cleanedInput = input.trim().toUpperCase();
    return cleanedInput.contains("0001-01-01T00:00:00Z");
  }

}
