package ru.terra.spending.core.db.entity;

import ru.terra.spending.core.Constants;
import android.net.Uri;
import android.provider.BaseColumns;

public interface TypeDBEntity extends BaseColumns
{
	String CONTENT_DIRECTORY = "types";
	Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + CONTENT_DIRECTORY);
	String CONTENT_TYPE = "entity.cursor.dir/types";
	String CONTENT_ITEM_TYPE = "entity.cursor.item/types";

	String NAME = "name";
	String ID = "server_id";
}
