package ru.terra.spending.core.db;

import roboguice.inject.ContextSingleton;
import ru.terra.spending.R;
import ru.terra.spending.core.Constants;
import ru.terra.spending.core.IOHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class ProjectDbOpenHelper extends SQLiteOpenHelper
{

	Context context;

	private int getResourceId(int oldver, int newver)
	{
		if (oldver == 1 && newver == 2)
			return R.raw.patch1to2;
		else if (oldver == 1 && newver == 3)
			return R.raw.patch1to3;
		else if (oldver == 2 && newver == 3)
			return R.raw.patch2to3;
		return 0;
	}

	@Inject
	public ProjectDbOpenHelper(Context context)
	{
		super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
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
//		if (newVersion > oldVersion)
//		{
//			int stages = newVersion - oldVersion;
//			String sql = IOHelper.readResourceAsString(context, getResourceId(oldVersion, newVersion);
//			String[] strings = sql.split(";");
//			executeStatements(strings, db);
//		}
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
