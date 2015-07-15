package ru.terra.spending.android.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import org.apache.http.message.BasicNameValuePair;
import ru.terra.spending.android.core.constants.Constants;
import ru.terra.spending.android.core.constants.URLConstants;
import ru.terra.spending.android.core.db.entity.TransactionDBEntity;
import ru.terra.spending.android.core.network.JsonAbstractProvider;
import ru.terra.spending.android.core.network.dto.IntegerResultDTO;
import ru.terra.spending.android.core.network.dto.TransactionDTO;
import ru.terra.spending.android.core.network.dto.TransactionListDTO;
import ru.terra.spending.android.util.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsJsonProvider extends JsonAbstractProvider {

    public TransactionsJsonProvider(Activity c) {
        super(c);
    }

    public void syncTransactions() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cntxActivity);
        Long lastSyncDate = prefs.getLong(Constants.CONFIG_LAST_SYNC_TRANSACTIONS_TO_SERVER, 0L);
        try {
            Cursor c = cntxActivity.getContentResolver().query(TransactionDBEntity.CONTENT_URI, null, TransactionDBEntity.SERVER_ID + " = -1", null,
                    null);
            List<TransactionDTO> dtos = new ArrayList<TransactionDTO>();
            if (c.moveToFirst())
                dtos.add(new TransactionDTO(c));
            while (c.moveToNext())
                dtos.add(new TransactionDTO(c));
            for (TransactionDTO dto : dtos) {
                String json = httpReqHelper.runJsonRequest(URLConstants.MobileTransactions.MOBILE_TRANSACTIONS +
                        URLConstants.MobileTransactions.DO_REGISTER,
                        new BasicNameValuePair(URLConstants.MobileTransactions.PARAM_DATE, dto.date.toString()),
                        new BasicNameValuePair(URLConstants.MobileTransactions.PARAM_MONEY, dto.value.toString()),
                        new BasicNameValuePair(URLConstants.MobileTransactions.PARAM_TYPE, dto.type.toString())
                );
                IntegerResultDTO res = new Gson().fromJson(json, IntegerResultDTO.class);
                Logger.i("TransactionsJsonProvider", "pushed: " + res.data);
                if (res.data == 0)
                    Logger.i("TransactionsJsonProvider", "Error: " + res.errorMessage);
                ContentValues cv = new ContentValues();
                cv.put(TransactionDBEntity.SERVER_ID, res.data);
                int updated = cntxActivity.getContentResolver().update(TransactionDBEntity.CONTENT_URI, cv, TransactionDBEntity._ID + " = ?",
                        new String[]{dto.id.toString()});
                Logger.i("TransactionsJsonProvider", "updated : " + updated);
            }

            c.close();

            String json = httpReqHelper.runSimpleJsonRequest(URLConstants.MobileTransactions.MOBILE_TRANSACTIONS + URLConstants.DoJson.DO_LIST);
            TransactionListDTO transactions = new Gson().fromJson(json, TransactionListDTO.class);
            for (TransactionDTO transactionDTO : transactions.data) {
                if (!checkExists(transactionDTO.id)) {
                    ContentValues cv = new ContentValues();
                    cv.put(TransactionDBEntity.MONEY, Double.parseDouble(transactionDTO.value.toString()));
                    cv.put(TransactionDBEntity.DATE, transactionDTO.date);
                    cv.put(TransactionDBEntity.TYPE, transactionDTO.type);
                    cv.put(TransactionDBEntity.SERVER_ID, transactionDTO.id);
                    cntxActivity.getContentResolver().insert(TransactionDBEntity.CONTENT_URI, cv);
                }
            }

            lastSyncDate = new Date().getTime();
        } catch (Exception e) {
            Logger.i("TransactionsJsonProvider", "error: " + e.getMessage());
            lastSyncDate = 0L;
            e.printStackTrace();
        }
        Editor editor = prefs.edit();
        editor.putLong(Constants.CONFIG_LAST_SYNC_TRANSACTIONS_TO_SERVER, lastSyncDate);
        editor.commit();
    }

    private boolean checkExists(Integer serverId) {
        Cursor c = null;
        try {
            c = cntxActivity.getContentResolver().query(TransactionDBEntity.CONTENT_URI, null, TransactionDBEntity.SERVER_ID + " = " + serverId.toString(), null, null);
            return c.moveToFirst();
        } finally {
            if (c != null)
                c.close();
        }

    }

}
