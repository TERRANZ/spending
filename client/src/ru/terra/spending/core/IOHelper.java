package ru.terra.spending.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class IOHelper
{

	public static final String SEPARATOR = "\n";

	public static String readResourceAsString(Context context, int resourceId)
	{
		InputStream inputStream = context.getResources().openRawResource(resourceId);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		String str;
		try
		{
			while ((str = br.readLine()) != null)
			{
				sb.append(str).append(SEPARATOR);
			}
		} catch (IOException e)
		{
			Log.d(IOHelper.class.getSimpleName(), "error loading resource by id = " + resourceId, e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
}
