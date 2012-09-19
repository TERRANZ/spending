package ru.terra.spending.core;

import ru.terra.spending.core.db.ProjectDbOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DatabaseHelper
{

	private SQLiteOpenHelper openHelper;

	@Inject
	public DatabaseHelper(ProjectDbOpenHelper openHelper)
	{
		this.openHelper = openHelper;
	}

	public void createTransaction(boolean readOnly)
	{
		SQLiteDatabase sqLiteDatabase = readOnly ? openHelper.getReadableDatabase() : openHelper.getWritableDatabase();
		sqLiteDatabase.beginTransaction();
	}

	public void createTransaction()
	{
		createTransaction(false);
	}

	public void markTransactionAsSuccess()
	{
		openHelper.getWritableDatabase().setTransactionSuccessful();
	}

	public void endTransaction()
	{
		openHelper.getWritableDatabase().endTransaction();
	}

	public boolean inTransaction()
	{
		return openHelper.getWritableDatabase().inTransaction();
	}

	public void closeDb()
	{
		SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
		if (writableDatabase.isOpen())
		{
			writableDatabase.close();
		}
	}

	public void executeSQL(String sql, Object[] params)
	{
		SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
		writableDatabase.execSQL(sql, params);
	}

	public Cursor executeRawSQL(String sql, String[] params)
	{
		SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
		return writableDatabase.rawQuery(sql, params);
	}

	public Cursor executeRawSQL(SQLiteQueryBuilder builder, String[] projection, String selection, String[] selectionArgs, String groupBy,
			String having, String sortOrder, String limit)
	{
		SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
		if (limit == null)
		{
			return builder.query(writableDatabase, projection, selection, selectionArgs, groupBy, having, sortOrder);
		}
		else
		{
			return builder.query(writableDatabase, projection, selection, selectionArgs, groupBy, having, sortOrder, limit);
		}
	}
}
