package ru.terra.spending.core.network.dto;

import android.database.Cursor;
import ru.terra.spending.core.db.entity.TransactionDBEntity;

public class TransactionDTO {
    public Integer id;
    public Long date;
    public Double value;
    public Integer type;

    public TransactionDTO() {
        id = 0;
        date = 0l;
        value = 0d;
        type = 0;
    }

    public TransactionDTO(Cursor c) {
        id = c.getInt(c.getColumnIndex(TransactionDBEntity._ID));
        date = c.getLong(c.getColumnIndex(TransactionDBEntity.DATE));
        type = c.getInt(c.getColumnIndex(TransactionDBEntity.TYPE));
        value = c.getDouble(c.getColumnIndex(TransactionDBEntity.MONEY));
    }
}
