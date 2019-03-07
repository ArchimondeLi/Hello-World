package com.huamai.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类tag.
 * 
 * @author n004881
 * @since [产品/模块版本]
 */
public abstract class DateUtils {

	/**
	 * 默认格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String TIME_PATTERN = "HH:mm:ss";

	public static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

	@SuppressWarnings("unused")
	private static String[] parsePatterns = { DATE_PATTERN, DATE_PATTERN + TIME_PATTERN, "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	public static String date2Str(Date target) {
		return date2Str(target, DATE_PATTERN);
	}

	public static String getNowTime() {
		// 获取当前时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);// 可以方便地修改日期格式
		String updateTime = dateFormat.format(now);
		return updateTime;
	}

	/**
	 * 时间对象转换成字符串对象.
	 * 
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static String date2Str(Date target, String pattern) {
		if (null == target) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String targetStr = "";
		try {
			targetStr = sdf.format(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetStr;
	}

	/**
	 * 时间加减计算.
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date date2Sub(Date date, int field, int offset) {
		Calendar c = Calendar.getInstance();

		c.setTime(date);
		c.add(field, offset);

		return c.getTime();
	}

	/**
	 * 获取时间戳tag.
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getTimeStamp(Date date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s1 = format.format(date);
		String s2 = format.format(new Date());
		return (int) ((format.parse(s2).getTime() - format.parse(s1).getTime()) / 1000);
	}


	/**
	 * 字符串对象转换成时间对象.
	 * 
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static Date str2Date(String target, String pattern) {
		if (null == target) {
			return new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间字符串转换成字符串tag.
	 * 
	 * @param src
	 * @return
	 */
	public static String str2DateFormat(String src) {
		if (src == null || "".equals(src)) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long seconds = 0;
			seconds = Long.parseLong(src);
			return sdf.format(new Date(seconds));
		} catch (NumberFormatException e) {
			return src;
		}
	}

	/**
	 * 比较两个时间大小
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return beginTime<endTime 返true
	 */
	public static boolean comparetoTime(String beginTime, String endTime, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date bt = sdf.parse(beginTime);
		Date et = sdf.parse(endTime);
		if (bt.before(et)) {
			// 表示bt小于et 
			return true;
		} else {
			// --反之
			if (beginTime.equals(endTime)) {
				return true;
			} else {
				return false;
			}

		}
	}

	/**
	 * 计算时间差 返回秒
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static long dateMethod(String beginTime, String endTime,String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date date1 = df.parse(beginTime);
			Date date2 = df.parse(endTime);
			long diff = date1.getTime() - date2.getTime();
			//System.out.println("毫秒数：" + diff);
			// 计算两个时间之间差了多少秒
			long minutes = diff / (1000);
			return minutes;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String args[]) {
		try {
			boolean falg = DateUtils.comparetoTime("20181117125715", "20181117125715", "yyyyMMddHHmmss");
			System.out.println(falg);
			long min = DateUtils.dateMethod("2019-01-04 00:00:30", getNowTime(), "yyyy-MM-dd HH:mm:ss");
			System.out.println(min);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
