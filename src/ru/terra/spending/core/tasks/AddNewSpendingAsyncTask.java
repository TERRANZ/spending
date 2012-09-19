package ru.terra.spending.core.tasks;

import ru.terra.spending.core.db.entity.Transaction;
import ru.terra.spending.core.db.entity.TransactionDBEntity;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

public class AddNewSpendingAsyncTask extends AsyncTask<Transaction, Void, Void>
{

	private Context context;

	public AddNewSpendingAsyncTask(Context context)
	{
		this.context = context;
	}

	@Override
	protected Void doInBackground(Transaction... params)
	{
		ContentValues tcv = new ContentValues();
		tcv.put(TransactionDBEntity.DATE, params[0].date.toString());
		tcv.put(TransactionDBEntity.COMMENT, params[0].comment);
		tcv.put(TransactionDBEntity.MONEY, params[0].money.toString());
		tcv.put(TransactionDBEntity.TYPE, params[0].type.id);
		context.getContentResolver().insert(TransactionDBEntity.CONTENT_URI, tcv);
		return null;
	}
}