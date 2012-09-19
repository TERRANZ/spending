package ru.terra.spending.activity.components;

import ru.terra.spending.core.db.entity.TypeDBEntity;
import android.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TypesSpinnerAdapter extends CursorAdapter
{
	public TypesSpinnerAdapter(Context context, Cursor c)
	{
		super(context, c, true);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		((TextView) view.findViewById(R.id.text1)).setText(cursor.getString(cursor.getColumnIndex(TypeDBEntity.NAME)));
		view.setTag(cursor.getLong(cursor.getColumnIndex(TypeDBEntity._ID)));
	}

	@Override
	public View newView(final Context context, Cursor cursor, ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.simple_spinner_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}

}
