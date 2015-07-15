package ru.terra.spending.android.core.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;
import ru.terra.spending.android.provider.TransactionsJsonProvider;

public class SyncTransactionsAsyncTask extends AsyncTask<Void, Void, Void> {
    private Activity activity;

    public SyncTransactionsAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        TransactionsJsonProvider transactionsJsonProvider = new TransactionsJsonProvider(activity);
        transactionsJsonProvider.syncTransactions();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(activity, "Траты синхронизированы", Toast.LENGTH_SHORT).show();
    }
}
