package ru.terra.spending.core.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;
import ru.terra.spending.provider.TransactionsJsonProvider;

public class PushTransactionsAsyncTask extends AsyncTask<Void, Void, Void> {
    private Activity activity;

    public PushTransactionsAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        new TransactionsJsonProvider(activity).pushTransactions();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(activity, "Траты отосланы", Toast.LENGTH_SHORT).show();
    }
}
