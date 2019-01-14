package com.symbio.bigdata.util;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.S";
	
	public static String formatToDateStr(LocalDate date, String format) {
		if (date == null) {
			return "";
		}
		
		if (StringUtils.isEmpty(format)) {
			format = DATE_PATTERN;
		}

		return DateTimeFormatter.ofPattern(DATE_PATTERN).format(date);
	}
	
	public static String formatToDateStr(LocalDate date) {

		return formatToDateStr(date, DATE_PATTERN);
	}
	
	public static LocalDate getLastTwelveWeekDate(LocalDate date) {
		return date.plusWeeks(-11);
	}
	
	public static LocalDate getLastTwelveMonthDate(LocalDate date) {
		return date.plusMonths(-11);
	}
	public static LocalDate getFirstDayOfWeek(LocalDate date) {
		return date.with(WeekFields.of(Locale.US).dayOfWeek(), 1);
	}
	
	public static LocalDate getFirstDayOfMonth(LocalDate date) {
		return date.withDayOfMonth(1);
	}
	
	public static LocalDate parseDate(String dateStr) {
		try {
			return LocalDate.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
	//将String字符串转换为yyyy-MM-dd的Date类型
	public static LocalDate parseDateTime(String dateStr) {
		try {
			return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_PATTERN));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * date2Local
	 * @param date
	 * @return
	 */
	public static LocalDate date2Local(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();

		// atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
		LocalDate localDate = instant.atZone(zoneId).toLocalDate();

		return localDate;
	}

	/**
	 * local2date
	 * @param localDate
	 * @return
	 */
	public static Date local2date(LocalDate localDate){
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate1 = localDate;
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

		Date date = Date.from(zdt.toInstant());
		return date;
	}

}
