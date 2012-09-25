package ru.terra.spending.core.tasks;

import ru.terra.spending.provider.TransactionsJsonProvider;
import android.app.Activity;
import android.os.AsyncTask;

public class PushTransactionsAsyncTask extends AsyncTask<Void, Void, Void>
{
	private Activity activity;

	public PushTransactionsAsyncTask(Activity activity)
	{
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		new TransactionsJsonProvider(activity).pushTransactions();
		return null;
	}
}
