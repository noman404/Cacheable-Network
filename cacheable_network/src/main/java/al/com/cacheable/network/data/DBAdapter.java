package al.com.cacheable.network.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Al Noman on 8/17/2017.
 */

public class DBAdapter implements DBKeys {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    private static volatile DBAdapter mInstance = null;

    private DBAdapter() {
    }

    public synchronized static DBAdapter getInstance(Context context) {

        if (mInstance == null) {
            synchronized (DBAdapter.class) {
                if (mInstance == null) {
                    mInstance = new DBAdapter().open(context);
                }
            }
        }

        return mInstance;
    }

    private DBAdapter open(Context context) {
        dbHelper = DBHelper.getInstance(context.getApplicationContext());
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
        db.close();
    }


    //insert into text table
    public long insertIntoCachedData(
            String url,
            String serverResponse,
            String params,
            String createdAt) {

        ContentValues value = new ContentValues();

        value.put(CACHED_DATA, serverResponse);
        value.put(URL, url);
        value.put(REQUEST_PARAM, params);
        value.put(CREATED_AT, createdAt);

        return db.insert(TABLE_CACHE, null, value);
    }

    public boolean isCacheAvailable(String url) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CACHE + " WHERE " + URL + "='" + url + "'", null);

        return null != cursor && cursor.getCount() > 0;
    }

    public boolean isCacheAvailable(String url, String params) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CACHE + " WHERE " + URL + "='" + url + "' AND " + REQUEST_PARAM + "='" + params + "'", null);

        return null != cursor && cursor.getCount() > 0;
    }

    public String getCachedDataByUrl(String url) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CACHE + " WHERE " + URL + "='" + url + "'", null);

        if (null != cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(CACHED_DATA));
        }
        return null;
    }

    public boolean updateCacheByUrl(String url, String data, String params) {
        ContentValues value = new ContentValues();
        value.put(CACHED_DATA, data);

        return db.update(TABLE_CACHE, value, URL + "='" + url + "' AND " + REQUEST_PARAM + "='" + params + "'", null) > 0;
    }

    public boolean delete(String url) {
        return db.delete(TABLE_CACHE, URL + "='" + url + "'", null) > 0;
    }
}
