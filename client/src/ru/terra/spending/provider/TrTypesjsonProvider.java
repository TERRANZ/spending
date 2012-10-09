package ru.terra.spending.provider;

import ru.terra.spending.core.constants.URLConstants;
import ru.terra.spending.core.db.entity.TypeDBEntity;
import ru.terra.spending.core.network.JsonAbstractProvider;
import ru.terra.spending.core.network.dto.TypeDTO;
import ru.terra.spending.util.Logger;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;

public class TrTypesjsonProvider extends JsonAbstractProvider
{

	public TrTypesjsonProvider(Activity c)
	{
		super(c);
	}

	public void sync()
	{
		try
		{
			String json = httpReqHelper.runSimpleJsonRequest(URLConstants.DoJson.TYPES_GET_TYPES_JSON);
			TypeDTO[] types = new Gson().fromJson(json, TypeDTO[].class);
			for (TypeDTO type : types)
			{
				Cursor c = cntxActivity.getContentResolver().query(TypeDBEntity.CONTENT_URI, null, TypeDBEntity.ID + " = ?",
						new String[] { type.id.toString() }, null);
				if (!c.moveToFirst())
				{
					ContentValues cv = new ContentValues();
					cv.put(TypeDBEntity.ID, type.id);
					cv.put(TypeDBEntity.NAME, type.name);
					Logger.i("TrTypesjsonProvider", "loaded type : " + type.id + " " + type.name);
					cntxActivity.getContentResolver().insert(TypeDBEntity.CONTENT_URI, cv);
				}
				c.close();
			}
		} catch (Exception e)
		{

		}
	}
}
