package ru.terra.spending.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import org.apache.http.message.BasicNameValuePair;
import ru.terra.spending.core.constants.Constants;
import ru.terra.spending.core.constants.URLConstants;
import ru.terra.spending.core.db.entity.TransactionDBEntity;
import ru.terra.spending.core.network.JsonAbstractProvider;
import ru.terra.spending.core.network.dto.OperationResultDTO;
import ru.terra.spending.core.network.dto.TransactionDTO;
import ru.terra.spending.util.Logger;
import ru.terra.spending.util.SettingsUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsJsonProvider extends JsonAbstractProvider {

    public TransactionsJsonProvider(Activity c) {
        super(c);
    }

    public void pushTransactions() {
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
                String json = httpReqHelper.runJsonRequest(
                        URLConstants.DoJson.MobileTransactions.MT_REG_TR.URL,
                        new BasicNameValuePair(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_UID, SettingsUtil.getSetting(cntxActivity,
                                Constants.CONFIG_UID, "1")), new BasicNameValuePair(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_TYPE,
                        dto.type.toString()),
                        new BasicNameValuePair(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_MONEY, dto.value.toString()),
                        new BasicNameValuePair(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_DATE, dto.date.toString()));
                OperationResultDTO res = new Gson().fromJson(json, OperationResultDTO.class);
                Logger.i("pushTransacton", "pushed: " + res.retid);
                ContentValues cv = new ContentValues();
                cv.put(TransactionDBEntity.SERVER_ID, res.retid);
                int updated = cntxActivity.getContentResolver().update(TransactionDBEntity.CONTENT_URI, cv, TransactionDBEntity._ID + " = ?",
                        new String[]{dto.id.toString()});
                Logger.i("pushTransacton", "updated : " + updated);
            }
            lastSyncDate = new Date().getTime();
        } catch (Exception e) {
            Logger.i("pushTransacton", "error: " + e.getMessage());
            lastSyncDate = 0L;
            e.printStackTrace();
        }
        Editor editor = prefs.edit();
        editor.putLong(Constants.CONFIG_LAST_SYNC_TRANSACTIONS_TO_SERVER, lastSyncDate);
        editor.commit();
    }
}
