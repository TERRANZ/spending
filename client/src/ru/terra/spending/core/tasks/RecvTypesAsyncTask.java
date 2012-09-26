package ru.terra.spending.core.tasks;

import ru.terra.spending.provider.TrTypesjsonProvider;
import android.app.Activity;
import android.os.AsyncTask;

public class RecvTypesAsyncTask extends AsyncTask<Void, Void, Void>
{

	private Activity activity;

	public RecvTypesAsyncTask(Activity activity)
	{
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		new TrTypesjsonProvider(activity).sync();
		return null;
	}

}
