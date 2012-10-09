package ru.terra.spending.core.tasks;

import ru.terra.spending.core.constants.ActivityConstants;
import ru.terra.spending.core.network.WorkIsDoneListener;
import ru.terra.spending.provider.LoginProvider;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class LoginAsyncTask extends AsyncTask<String, Void, Boolean>
{

	private Activity context;
	private WorkIsDoneListener widl;
	private ProgressDialog dlg;

	public LoginAsyncTask(Activity context, WorkIsDoneListener widl)
	{
		super();
		this.context = context;
		this.widl = widl;
		dlg = ProgressDialog.show(context, "Вход", "Входим");
	}

	@Override
	protected Boolean doInBackground(String... params)
	{
		return new LoginProvider(context).login(params[0], params[1]);
	}

	@Override
	protected void onPostExecute(Boolean result)
	{
		if (dlg.isShowing())
			dlg.dismiss();
		if (widl != null)
			widl.workIsDone(ActivityConstants.LOGIN, result.toString());
	}
}
