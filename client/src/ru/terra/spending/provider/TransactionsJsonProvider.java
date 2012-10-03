package ru.terra.spending.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import ru.terra.spending.core.Constants;
import ru.terra.spending.core.db.entity.TransactionDBEntity;
import ru.terra.spending.core.network.JsonAbstractProvider;
import ru.terra.spending.core.network.dto.OperationResultDTO;
import ru.terra.spending.core.network.dto.TransactionDTO;
import ru.terra.spending.util.Logger;

public class TransactionsJsonProvider extends JsonAbstractProvider
{

	public TransactionsJsonProvider(Activity c)
	{
		super(c);
	}

	public void pushTransactions()
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cntxActivity);
		Long lastSyncDate = prefs.getLong(Constants.CONFIG_LAST_SYNC_TRANSACTIONS_TO_SERVER, 0L);
		try
		{
			Cursor c = cntxActivity.getContentResolver().query(TransactionDBEntity.CONTENT_URI, null, TransactionDBEntity.DATE + " >= ?",
					new String[] { lastSyncDate.toString() }, null);
			List<TransactionDTO> dtos = new ArrayList<TransactionDTO>();
			if (c.moveToFirst())
				dtos.add(new TransactionDTO(c));
			while (c.moveToNext())
				dtos.add(new TransactionDTO(c));
			for (TransactionDTO dto : dtos)
			{
				String json = httpReqHelper.runJsonRequest("/mobiletransaction/do.transaction.register.json",
						new BasicNameValuePair("uid", String.valueOf(prefs.getInt("userid", 1))),
						new BasicNameValuePair("type", dto.type.toString()), new BasicNameValuePair("money", dto.value.toString()),
						new BasicNameValuePair("date", dto.date.toString()));
				OperationResultDTO res = new Gson().fromJson(json, OperationResultDTO.class);
				Logger.i("pushTransacton", "pushed: " + res.retid);
			}
			lastSyncDate = new Date().getTime();
		} catch (Exception e)
		{
			Logger.i("pushTransacton", "error: " + e.getMessage());
			lastSyncDate = 0L;
			e.printStackTrace();
		}
		Editor editor = prefs.edit();
		editor.putLong(Constants.CONFIG_LAST_SYNC_TRANSACTIONS_TO_SERVER, lastSyncDate);
		editor.commit();
	}
}
