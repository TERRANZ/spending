package ru.terra.spending.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class ProjectModule extends AbstractModule
{

	private Context context;

	@Inject
	public ProjectModule(Context context)
	{
		this.context = context;
	}

	@Override
	protected void configure()
	{
	}

	@Provides
	@Named("jsonServer")
	public String provideJsonServerName()
	{
		return "http://192.168.1.1:8080/";
	}
	
	@Provides
	@Named("dateFormat")
	@Singleton
	public DateFormat provideDateFormat()
	{
		return new SimpleDateFormat("dd.MM.yyyy");
	}

	@Provides
	@Named("exportDateFormat")
	@Singleton
	public DateFormat provideExportDateFormat()
	{
		return new SimpleDateFormat("dd-MM-yyyy");
	}

	@Provides
	@Named("dateFullFormat")
	@Singleton
	public DateFormat provideFullDateFormat()
	{
		return new SimpleDateFormat("dd MMMMM yyyy");
	}

	@Provides
	@Named("timeFormat")
	@Singleton
	public DateFormat provideTimeFormat()
	{
		return new SimpleDateFormat("HH:mm");
	}

	@Provides
	@Named("dateTimeFormat")
	@Singleton
	public DateFormat provideDateTimeFormat()
	{
		return new SimpleDateFormat("dd.MM.yyyy HH:mm");
	}

	@Provides
	@Named("exportDateTimeFormat")
	@Singleton
	public DateFormat provideExportDateTimeFormat()
	{
		return new SimpleDateFormat("dd-MM-yyyy HH:mm");
	}

	@Provides
	@Named("dayOfWeekFormat")
	@Singleton
	public DateFormat provideDayOfWeekFormat()
	{
		return new SimpleDateFormat("dd MMMMM, EEEEEEE");
	}

	@Provides
	@Named("dayOfMonthFormat")
	@Singleton
	public DateFormat provideDayOfMonthFormat()
	{
		return new SimpleDateFormat("dd MMMMM");
	}
}
