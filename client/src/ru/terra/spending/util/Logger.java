package ru.terra.spending.util;

import android.util.Log;

public class Logger
{
	public static void i(String tag, String msg)
	{
		if (tag != null && msg != null)
			Log.i(tag, msg);
	};

	public static void w(String tag, String msg)
	{
		if (tag != null && msg != null)
			Log.w(tag, msg);
	};

	public static void w(String tag, String msg, Throwable t)
	{
		if (tag != null && msg != null)
			Log.w(tag, msg, t);
	};

	public static void d(String tag, String msg)
	{
		if (tag != null && msg != null)
			Log.d(tag, msg);
	};
}
