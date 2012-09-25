package ru.terra.spending.core.network;


import android.app.Activity;

public abstract class JsonAbstractProvider
{
	protected HttpRequestHelper httpReqHelper;
	protected Activity cntxActivity;

	public JsonAbstractProvider(Activity c)
	{
		this.cntxActivity = c;
		httpReqHelper = new HttpRequestHelper(c);
	}
}
