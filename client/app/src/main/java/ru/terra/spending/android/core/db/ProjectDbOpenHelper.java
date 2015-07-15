package ru.terra.spending.android.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import ru.terra.spending.android.R;
import ru.terra.spending.android.core.constants.Constants;
import ru.terra.spending.android.core.helper.IOHelper;

@ContextSingleton
public class ProjectDbOpenHelper extends SQLiteOpenHelper {

    Context context;

    private int getResourceId(int oldver, int newver) {
        return 0;
    }

    @Inject
    public ProjectDbOpenHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ProjectDbOpenHelper", "onCreate()");
        String sql = IOHelper.readResourceAsString(context, R.raw.create_db);
        String[] strings = sql.split(";");
        executeStatements(strings, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if (newVersion > oldVersion)
        // {
        // int stages = newVersion - oldVersion;
        // String sql = IOHelper.readResourceAsString(context, getResourceId(oldVersion, newVersion);
        // String[] strings = sql.split(";");
        // executeStatements(strings, db);
        // }
    }

    private void executeStatements(String[] strings, SQLiteDatabase db) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i].replace("\n", "").trim().replace("\t", "");
            if (str.length() > 0) {
                db.execSQL(strings[i]);
            }
        }
    }
}
