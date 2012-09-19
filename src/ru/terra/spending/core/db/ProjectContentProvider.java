package ru.terra.spending.core.db;

import roboguice.content.RoboContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.google.inject.Inject;

public class ProjectContentProvider extends RoboContentProvider
{

	@Inject
	ProjectDbOpenHelper openHelper;

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(parsePath(uri));
		SQLiteDatabase db = openHelper.getWritableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri)
	{
		return "";
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues)
	{
		SQLiteDatabase db = openHelper.getWritableDatabase();
		long rowId = db.insert(parsePath(uri), null, initialValues);
		if (rowId > 0)
		{
			Uri noteUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs)
	{
		SQLiteDatabase db = openHelper.getWritableDatabase();
		int i = db.delete(parsePath(uri), where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return i;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs)
	{
		SQLiteDatabase db = openHelper.getWritableDatabase();
		int count = db.update(parsePath(uri), values, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	public String parsePath(Uri uri)
	{
		String path = uri.getPath();
		return parsePath(path);
	}

	public String parsePath(String path)
	{
		// TODO implement method correctly using RegExp
		// When update gnerates must return following string /table_name/id
		int index = path.indexOf("/");
		if (index < 0)
		{
			return path;
		}
		else
		{
			index = path.lastIndexOf("/");
			if (index > 0)
			{
				String str = path.substring(index + 1, path.length());
				try
				{
					Integer.valueOf(str);
					return parsePath(path.substring(0, index));
				} catch (NumberFormatException e)
				{
					return str;
				}
			}
			else
			{
				return path.replace("/", "");
			}
		}
	}

	public void closeDb()
	{
		SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
		if (writableDatabase.isOpen())
		{
			writableDatabase.close();
		}
	}

	public void beginTransaction(boolean readonly)
	{
		SQLiteDatabase db = readonly ? openHelper.getReadableDatabase() : openHelper.getWritableDatabase();
		db.beginTransaction();
	}

	public void beginTransaction()
	{
		beginTransaction(false);
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
}
