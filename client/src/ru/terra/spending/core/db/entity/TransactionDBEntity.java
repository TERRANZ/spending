package ru.terra.spending.core.db.entity;

import ru.terra.spending.core.Constants;
import android.net.Uri;
import android.provider.BaseColumns;

public interface TransactionDBEntity extends BaseColumns
{
	String CONTENT_DIRECTORY = "tr";
	Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + CONTENT_DIRECTORY);
	String CONTENT_TYPE = "entity.cursor.dir/tr";
	String CONTENT_ITEM_TYPE = "entity.cursor.item/tr";

	String DATE = "t_date";
	String MONEY = "t_money";
	String TYPE = "t_type";
	String COMMENT = "t_comment";
}
