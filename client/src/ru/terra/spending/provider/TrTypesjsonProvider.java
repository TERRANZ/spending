package ru.terra.spending.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import ru.terra.spending.core.constants.URLConstants;
import ru.terra.spending.core.db.entity.TypeDBEntity;
import ru.terra.spending.core.network.JsonAbstractProvider;
import ru.terra.spending.core.network.dto.ListTypeDTO;
import ru.terra.spending.core.network.dto.TypeDTO;
import ru.terra.spending.util.Logger;

public class TrTypesjsonProvider extends JsonAbstractProvider {

    public TrTypesjsonProvider(Activity c) {
        super(c);
    }

    public void sync() {
        try {
            String json = httpReqHelper.runSimpleJsonRequest(URLConstants.Types.TYPES + URLConstants.DoJson.DO_LIST);
            ListTypeDTO types = new Gson().fromJson(json, ListTypeDTO.class);
            for (TypeDTO type : types.data) {
                Cursor c = cntxActivity.getContentResolver().query(TypeDBEntity.CONTENT_URI, null, TypeDBEntity.ID + " = ?",
                        new String[]{type.id.toString()}, null);
                if (!c.moveToFirst()) {
                    ContentValues cv = new ContentValues();
                    cv.put(TypeDBEntity.ID, type.id);
                    cv.put(TypeDBEntity.NAME, type.name);
                    Logger.i("TrTypesjsonProvider", "loaded type : " + type.id + " " + type.name);
                    cntxActivity.getContentResolver().insert(TypeDBEntity.CONTENT_URI, cv);
                }
                c.close();
            }
        } catch (Exception e) {
        }
    }
}
