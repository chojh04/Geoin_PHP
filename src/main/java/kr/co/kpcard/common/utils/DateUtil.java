package kr.co.kpcard.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{
	
	public final static String YYYYMMDDHH24MMSS = "yyyyMMddHHmmss";
	public final static String YYYYMMDD235959 = "yyyyMMdd235959";
	public final static String YYYYMMDD000000 = "yyyyMMdd000000";
	public final static String YYYYMMDD = "yyyyMMdd";
	
	public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT = "yyyy-MM-dd";
	
	public static int getCurrentYear()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	public static int getCurrentMonth()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	public static int getCurrentDay()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getCurrentHour()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getCurrentMinute()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}
	
	public static long getCurrentTimeMillis()
	{
		return System.currentTimeMillis();
	}
	
	public static Date addYear(Date date, int years)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		
		return cal.getTime();
	}
	public static Date addMonth(Date date, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		
		return cal.getTime();
	}
	public static Date addDay(Date date, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, amount);
		
		return cal.getTime();
	}
	public static Date addHour(Date date, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, amount);
		
		return cal.getTime();
	}
	public static Date addMinute(Date date, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, amount);
		
		return cal.getTime();
	}
	public static Date addSecond(Date date, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, amount);
		
		return cal.getTime();
	}

	public static int getYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR);
	}
	public static int getMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.MONTH) + 1;
	}
	public static int getDay(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public static int getHour(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.HOUR);
	}
	public static int getMinute(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.MINUTE);
	}
	public static int getSecond(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.SECOND);
	}
	
	/**
	 * ������ ������ ��´�. ���� ù�� ° ������ �Ͽ����̸� 1�� ��Ÿ����.
	 * @return ����� ����
	 */
	public static int getDayOfWeek()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * �־��� ������ ������ ��´�.
	 * @param date
	 * @return �־��� ������ ����
	 */
	public static int getDayOfWeek(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * ������ �� °�ֿ� ���ϴ����� �ǵ�����.
	 * @return
	 */
	public static int getWeekOfMonth()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * �־��� ���ڰ� �� ���� �� °�ֿ� ���ϴ����� �ǵ�����.
	 * @param date
	 * @return
	 */
	public static int getWeekOfMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_MONTH);
	}
	
	public static Date createDate()
	{
		return new Date();
	}
	
	public static Date createDate(int year, int month, int date)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, date, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date createOriginDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(0, 0, 0, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date createDate(int year, int month, int date, int hour, int minute, int second)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, date, hour, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public static Date createDate(String dateString, String dateFormat) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
		return format.parse(dateString);
	}
	
	public static Date getDateString(String dateString, String dateFormat) 
	{
		Date result = null;
		
		try 
		{
			SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
			result = format.parse(dateString);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			return null;
		}
		
		return result;
	}
	
	
	
	public static int getWeekCount()
	{
		Calendar cal = Calendar.getInstance();
		
		// ù° ���� ������ ��´�.
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		// ������ ���� ��´�.
		int lastDay = cal.getMaximum(Calendar.DAY_OF_MONTH);
		
		// �� ���� �ָ� ��´�.
		int count = (dayOfWeek + lastDay - 1) / 7;

		if((dayOfWeek + lastDay - 1) % 7 > 0)
			count++;

		return count;
	}
	
	
	public static int getWeekCount(int year, int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, 1);
		
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int count = (dayOfWeek + lastDay - 1) / 7;
		
		if((dayOfWeek + lastDay - 1) % 7 > 0)
			count++;

		return count;
	}
	
	public static int getLastDayOfMonth()
	{
		Calendar cal = Calendar.getInstance();
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int getLastDayOfMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static String getCurrentDateTime()
	{
		Calendar cal = Calendar.getInstance();
		return getDateFormatString(cal.getTime(), DATE_TIME_FORMAT);
	}
	
	public static String getCurrenntDate()
	{
		Calendar cal = Calendar.getInstance();
		return getDateFormatString(cal.getTime(), DATE_FORMAT);
	}
	
	
	public static String getCurrentDate(String dateTimeFormat)
	{
		Calendar cal = Calendar.getInstance();
		return getDateFormatString(cal.getTime(), dateTimeFormat);
	}
	
	public static String getDateFormatString(Date date, String dateTimeFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		return sdf.format(date);
	}
	
//	public static void main(String[] args)
//	{
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.MONTH, 7);
//		cal.set(Calendar.DAY_OF_MONTH, 31);
//		System.out.println(cal.get(Calendar.WEEK_OF_MONTH));
//		System.out.println(cal.getFirstDayOfWeek());
//		
//		// 2008�� 8�� 31���� ���° ������, ���� ��������
//		Date date = DateUtil.createDate(2008, 8, 31);
//		System.out.println(DateUtil.getDateFormatString(date, "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateUtil.getWeekOfMonth(date));
//		System.out.println(DateUtil.getDayOfWeek(date));
//		
//		System.out.println(DateUtil.createDate().toGMTString());
//	}
}

