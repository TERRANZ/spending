package ru.terra.spending.activity.components;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.terra.spending.R;
import ru.terra.spending.core.db.entity.TransactionDBEntity;
import ru.terra.spending.core.db.entity.TypeDBEntity;
import ru.terra.spending.util.TimeUtil;

public class TransactionsListCursorAdapter extends CursorAdapter {

    public class ViewHolder {
        public Long id;
        public TextView date, type, comment, spending, sent;
    }

    private String getTypeName(Long id) {
        String ret = "не известно";
        Cursor c = mContext.getContentResolver().query(TypeDBEntity.CONTENT_URI, null, TypeDBEntity._ID + " = ?", new String[]{id.toString()},
                null);
        if (c.moveToFirst())
            ret = c.getString(c.getColumnIndex(TypeDBEntity.NAME));
        c.close();
        return ret;
    }

    public TransactionsListCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh = new ViewHolder();
        vh.date = (TextView) view.findViewById(R.id.tv_transaction_item_date);
        vh.type = (TextView) view.findViewById(R.id.tv_transaction_item_type);
        vh.comment = (TextView) view.findViewById(R.id.tv_transaction_item_comment);
        vh.spending = (TextView) view.findViewById(R.id.tv_transaction_item_spending);
        vh.sent = (TextView) view.findViewById(R.id.tvSent);
        Long ldate = cursor.getLong(cursor.getColumnIndex(TransactionDBEntity.DATE));
        vh.date.setText(TimeUtil.fromDate(ldate));
        Long typeId = cursor.getLong(cursor.getColumnIndex(TransactionDBEntity.TYPE));
        vh.type.setText(getTypeName(typeId));
        vh.spending.setText(cursor.getString(cursor.getColumnIndex(TransactionDBEntity.MONEY)));
        vh.comment.setText(cursor.getString(cursor.getColumnIndex(TransactionDBEntity.COMMENT)));
        vh.id = (cursor.getLong(cursor.getColumnIndex(TransactionDBEntity._ID)));
        if (cursor.getLong(cursor.getColumnIndex(TransactionDBEntity.SERVER_ID)) != -1)
            vh.sent.setVisibility(View.VISIBLE);
        else
            vh.sent.setVisibility(View.INVISIBLE);
        view.setTag(vh);
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.i_transaction_item, parent, false);
        ((TextView) v.findViewById(R.id.tvSent)).setTextColor(Color.GREEN);
        bindView(v, context, cursor);
        return v;
    }

}
