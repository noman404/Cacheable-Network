package al.com.cacheable.network.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Al Noman on 8/17/2017.
 */

class DBHelper extends SQLiteOpenHelper implements DBKeys {

    private static volatile DBHelper mInstance = null;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    static DBHelper getInstance(Context ctx) {

        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper(ctx.getApplicationContext());
                }
            }
        }
        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Map<String, String> tableText = new LinkedHashMap<>();

        tableText.put(ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
        tableText.put(CACHED_DATA, "TEXT");
        tableText.put(URL, "TEXT");
        tableText.put(REQUEST_PARAM, "TEXT");
        tableText.put(CREATED_AT, "TEXT");

        createTable(TABLE_CACHE, tableText, db);
    }

    private void createTable(String tableName, Map<String, String> fields, SQLiteDatabase db) {

        Iterator iterator = fields.entrySet().iterator();
        StringBuilder columns = new StringBuilder("(");
        Log.d("column", columns.toString());
        int counter = 1;

        while (iterator.hasNext()) {

            Map.Entry mEntry = (Map.Entry) iterator.next();
            columns.append(mEntry.getKey()).append(" ")
                    .append(mEntry.getValue());
            if (counter == fields.size()) {
                columns.append(")");
            } else {
                columns.append(" , ");
                counter++;
            }
        }
        if (counter > 1) {
            Log.d("column", columns.toString());
            String createNewTable = "CREATE TABLE IF NOT EXISTS " + tableName + " " + columns;
            db.execSQL(createNewTable);

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CACHE);
    }
}
