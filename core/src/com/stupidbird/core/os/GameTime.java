package com.stupidbird.core.os;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间通用处理
 * 
 * @author stupidbird
 * 
 */
public class GameTime {
	private static long msOffset = 0;

	/**
	 * 获取时间偏移
	 * 
	 * @return
	 */
	public static long getMsOffset() {
		return msOffset;
	}

	/**
	 * 设置时间偏移
	 * 
	 * @param msOffset
	 */
	public static void setMsOffset(long msOffset) {
		GameTime.msOffset = msOffset;
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		if (getMsOffset() != 0) {
			calendar.setTimeInMillis(calendar.getTimeInMillis() + getMsOffset());
		}
		return calendar;
	}

	/**
	 * 获取系统距1970年1月1日总毫秒
	 * 
	 * @return
	 */
	public static long getMillisecond() {
		return getCalendar().getTimeInMillis() + getMsOffset();
	}

	/**
	 * 获取系统距1970年1月1日总秒
	 * 
	 * @return
	 */
	public static int getSeconds() {
		return (int) ((getCalendar().getTimeInMillis() + getMsOffset()) / 1000);
	}

	/**
	 * 获取一年中的天
	 * 
	 * @return
	 */
	public static int getYearDay() {
		return getCalendar().get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 获取一年中的周
	 * 
	 * @return
	 */
	public static int getYearWeek() {
		return getCalendar().get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		Timestamp ts = new Timestamp(getMillisecond());
		return ts;
	}

	/**
	 * 获取当日0点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getAM0Date() {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的0点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getAM0Date(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 是否是相同的日子（月 和 天 相同）
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(long time1, long time2) {
		Calendar dt1 = getCalendar();
		Calendar dt2 = getCalendar();
		dt1.setTime(new Date(time1));
		dt2.setTime(new Date(time2));
		if (dt1.get(Calendar.MONTH) == dt2.get(Calendar.MONTH) && dt1.get(Calendar.DAY_OF_MONTH) == dt2.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为当天
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar dt1 = getCalendar();
		Calendar dt2 = getCalendar();
		dt2.setTime(date);
		if (dt1.get(Calendar.MONTH) == dt2.get(Calendar.MONTH) && dt1.get(Calendar.DAY_OF_MONTH) == dt2.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static String getTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(getCalendar().getTime());
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static String getTimeString(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(getCalendar().getTime());
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static String getDateString(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 字符串转换为日历格式
	 * 
	 * @param info
	 * @return
	 */
	public static Calendar stringToCalendar(String info) {
		SimpleDateFormat sdf = null;
		if (info.indexOf(" ") > 0) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else { 
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		try {
			sdf.parse(info);
		} catch (ParseException e) {
			GameMonitor.catchException(e);
		}
		return sdf.getCalendar();
	}
	
	/**
	 * 获取两个日期之间的天数差
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static int calendarDiff(Calendar cal1, Calendar cal2) {
		return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 365 - cal2.get(Calendar.DAY_OF_YEAR) + cal1.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * @return 获取第二天零点
	 */
	public static long getNextAM0Date() {
		Calendar calendar = getCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取两个时间相隔的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calcBetweenDays(Date startDate, Date endDate) {
		if ((startDate == null) || (endDate == null)) {
			return 0;
		}
		Date startDate0AM = getAM0Date(startDate);
		Date endDate0AM = getAM0Date(endDate);
		long v1 = startDate0AM.getTime() - endDate0AM.getTime();
		return Math.abs((int) divideAndRoundUp(v1, 86400000.0D, 0));
	}

	private static double divideAndRoundUp(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("the scale must be a positive integer or zero");
		}

		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);
		return bd1.divide(bd2, scale, 0).doubleValue();
	}

	/**
	 * 获得当前这一周的第一天，中国是周一
	 * 
	 * @return
	 */
	public static Date getFirstDayOfCurWeek() {
		Calendar calendar = getCalendar();
		return getFirstDayOfWeek(calendar);
	}
	
	/**
	 * 获得当前这一周的第一天，中国是周一
	 * 
	 * @return
	 */
	public static Calendar getFirstDayCalendarOfCurWeek() {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			calendar.add(Calendar.DATE, -6);
		} else {
			calendar.add(Calendar.DATE, (2 - dayOfWeek));
		}
		return calendar;
	}
	
	
	/**
	 * 获得指定某个日期那一周的第一天，中国是周一
	 * 
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		return getFirstDayOfWeek(calendar);
	}
	
	/**
	 * 获得指定某个日期那一周的第一天，中国是周一
	 * 
	 * @return
	 */
	public static Date getFirstDayOfWeek(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			calendar.add(Calendar.DATE, -6);
		} else {
			calendar.add(Calendar.DATE, (2 - dayOfWeek));
		}
		return calendar.getTime();
	}
}
