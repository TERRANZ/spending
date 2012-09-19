package ru.terra.spending.core.db;

import roboguice.inject.ContextSingleton;
import ru.terra.spending.R;
import ru.terra.spending.core.Constants;
import ru.terra.spending.core.IOHelper;
import ru.terra.spending.core.db.entity.TypeDBEntity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class ProjectDbOpenHelper extends SQLiteOpenHelper
{

	Context context;

	@Inject
	public ProjectDbOpenHelper(Context context)
	{
		super(context, Constants.DB_NAME, null, 3);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.i("ProjectDbOpenHelper", "onCreate()");
		String sql = IOHelper.readResourceAsString(context, R.raw.create_db);
		String[] strings = sql.split(";");
		executeStatements(strings, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO code that updates db schema should be here
	}

	private void executeStatements(String[] strings, SQLiteDatabase db)
	{
		for (int i = 0; i < strings.length; i++)
		{
			String str = strings[i].replace("\n", "").trim().replace("\t", "");
			if (str.length() > 0)
			{
				db.execSQL(strings[i]);
			}
		}
	}
}
