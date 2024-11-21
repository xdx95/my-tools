package com.my.tools.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author: xdx
 * @date: 2024/9/9
 * @description: 日期工具类
 */
public class DateUtils {

	// 默认时间格式
	public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

	// 获取当前时间字符串
	public static String getCurrentDateTime() {
		return LocalDateTime.now().format(DEFAULT_FORMATTER);
	}

	// 获取当前时间（指定格式）
	public static String getCurrentDateTime(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	// 字符串转换为 LocalDateTime
	public static LocalDateTime parseToDateTime(String dateTimeStr) {
		return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
	}

	// 字符串转换为 LocalDateTime（指定格式）
	public static LocalDateTime parseToDateTime(String dateTimeStr, String pattern) {
		return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
	}

	// LocalDateTime 转字符串
	public static String formatDateTime(LocalDateTime dateTime) {
		return dateTime.format(DEFAULT_FORMATTER);
	}

	// LocalDateTime 转字符串（指定格式）
	public static String formatDateTime(LocalDateTime dateTime, String pattern) {
		return dateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	// 计算两个日期之间的差异（以天为单位）
	public static long daysBetween(LocalDate startDate, LocalDate endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate);
	}

	// 计算两个时间之间的差异（以秒为单位）
	public static long secondsBetween(LocalDateTime startTime, LocalDateTime endTime) {
		return ChronoUnit.SECONDS.between(startTime, endTime);
	}

	// LocalDateTime 转换为时间戳（秒级）
	public static long toEpochSecond(LocalDateTime dateTime) {
		return dateTime.toEpochSecond(ZoneOffset.UTC);
	}

	// 时间戳（秒级）转换为 LocalDateTime
	public static LocalDateTime fromEpochSecond(long epochSecond) {
		return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
	}

	// 将 Date 转换为 LocalDateTime
	public static LocalDateTime dateToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	// 将 LocalDateTime 转换为 Date
	public static Date localDateTimeToDate(LocalDateTime dateTime) {
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	// 获取当前日期（不含时间）
	public static String getCurrentDate() {
		return LocalDate.now().toString();
	}

	// 获取当前时间（不含日期）
	public static String getCurrentTime() {
		return LocalTime.now().toString();
	}

}
