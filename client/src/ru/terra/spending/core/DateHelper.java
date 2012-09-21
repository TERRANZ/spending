package ru.terra.spending.core;

import java.util.Calendar;
import java.util.Date;

import android.text.format.DateUtils;

public class DateHelper
{

	public static Date getDateMidnight(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);

		return c.getTime();
	}

	public static Date addDays(Date base, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(base);
		c.add(Calendar.DAY_OF_YEAR, count);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}

	public static Date getDate(int year, int month, int day, int hour, int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getDate(int year, int month, int day)
	{
		return getDate(year, month, day, 0, 0);
	}

	public static Date getDate(long value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(value);
		return calendar.getTime();
	}

	public static Date getCurrentDateTime()
	{
		return Calendar.getInstance().getTime();
	}

	public static boolean isDayCurrent(Date date)
	{
		return DateUtils.isToday(date.getTime());
	}

	public static boolean isDayTheSame(Date date1, Date date2)
	{
		if (date1 == null || date2 == null)
		{
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date1);
		int day = c.get(Calendar.DAY_OF_YEAR);
		c.setTime(date2);
		return day == c.get(Calendar.DAY_OF_YEAR);
	}
}
