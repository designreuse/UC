package com.yealink.ofweb.modules.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类。
 * 
 * @author yjz
 */
public class DateUtil {
	private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * 按照传入的pattern对date进行格式化<br/>
	 * 如果日期为null，则不进行格式化返回空内容<br/>
	 * 如果Pattern为空或者空内容直接使用默认的格式：yyyy-MM-dd进行格式化
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @param pattern
	 *            格式化
	 * 
	 * @return string
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}

		if (pattern == null || pattern.isEmpty()) {
			pattern = DATE_FORMAT_YYYY_MM_DD;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return (sdf.format(date));
	}

	/**
	 * 将date格式为 yyyy-MM-dd HH:mm:ss<br/>
	 * 如果日期为null，则不进行格式化返回空内容<br/>
	 * 
	 * @param date
	 * 
	 * @return string
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 格式化日期为ISO8601的格式，例如20140324T16:33:22<br/>
	 * 如果日期为null，则不进行格式化返回空内容<br/>
	 * 
	 * @author lbt
	 * @since 2014-3-34
	 * @param date
	 *            日期
	 * @return 格式化后的日期
	 */
	public static String formatDateTimeIso8601(final Date date) {
		return formatDate(date, "yyyyMMdd'T'HH:mm:ss");
	}

	/**
	 * 将date格式为 yyyy-MM-dd<br/>
	 * 如果日期为null，则不进行格式化返回空内容<br/>
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return (formatDate(date, DATE_FORMAT_YYYY_MM_DD));
	}

	/**
	 * 将date格式为 HH:mm:ss<br/>
	 * 如果日期为null，则不进行格式化返回空内容<br/>
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return (formatDate(date, "HH:mm:ss"));
	}

	/**
	 * 将datetime解析为 yyyy-MM-dd HH:mm:ss<br/>
	 * 如果datetime为空或者空内容，则不进行解析，返回null
	 * 
	 * @param datetime
	 * 
	 * @return date
	 */
	public static Date parseDateTime(String datetime) {
		if (datetime == null || datetime.isEmpty()) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
		} catch (ParseException e) {
			return parseDate(datetime);
		}

	}

	/**
	 * 将datetime解析为 yyyy-MM-dd HH:mm<br/>
	 * 如果datetime为空或者空内容，则不进行解析，返回null
	 * 
	 * @param datetime
	 * 
	 * @return date
	 */
	public static Date parseDateTime2(String datetime) {
		if (datetime == null || datetime.isEmpty()) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(datetime);
		} catch (ParseException e) {
			return parseDate(datetime);
		}

	}

	/**
	 * 将date解析为 yyyy-MM-dd<br/>
	 * 如果date为空或者空内容，则不进行解析，返回null
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static Date parseDate(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(
				DATE_FORMAT_YYYY_MM_DD);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param field
	 *            时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
	 *            * Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减。
	 * 
	 * @return 变动后的时间
	 */
	private static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 给date加上amout指定的毫秒
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 
	 * 给date加上amout指定的秒
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 给date加上amout指定的分
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 给date加上amout指定的小时
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * 给date加上amout指定的天
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	/**
	 * 给date加上amout指定的月
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 给date加上amout指定的年
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 取得当前时间(时间格式:yyyyMMddHHmmss)
	 * 
	 * @return
	 */
	public static String getNowtimeYYYYMMDDHHMMSS() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}

	/**
	 * 取得当前日期（日期格式：yyyyMMdd）
	 * 
	 * @return 日期字符串
	 */
	public static String getNowdateYYYYMMDD() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String nowdate = format.format(date);
		return nowdate;
	}

	/**
	 * 解析GMT成东8区的时间，如果无法解析则使用parseDate来解析.
	 * 
	 * @author lbt
	 * @since 2014-12-27
	 * 
	 * @param date
	 *            日期字符串，例如Sun Jan 02 2014 14:23:44 GMT
	 * @return 日期对象
	 */
	public static Date parseGMTDateTime(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return parseDate(date);
		}
	}

	/**
	 * 校验起始日期和结束日期是否有效<br/>
	 * 如果statTime为null或者endTime为null则认为有效<br/>
	 * 如果起始日期和结束日期都不为空，校验起始日期是否大于结束日期.
	 * 
	 * @param startTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @return true:结束日期在开始日期之后或结束日期与开始日期相等
	 */
	public static boolean vaildateDate(Date startTime, Date endTime) {
		if (startTime == null || endTime == null) {
			return true;
		}
		if (startTime.equals(endTime)) {
			return true;
		}
		return endTime.after(startTime);
	}

	/**
	 * 计算两个日期之间相差的天数<br/>
	 * 如果开始时间或者结束时间为null，则返回0<br/>
	 * 如果开始时间大于结束时间则返回<0的相差天数.
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date start, Date end) {
		if (start == null || end == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		long startTimeMillis = cal.getTimeInMillis();
		cal.setTime(end);
		long endTimeMillis = cal.getTimeInMillis();
		long betweenDays = (endTimeMillis - startTimeMillis)
				/ (1000L * 3600 * 24);
		return (int) betweenDays;
	}
}
