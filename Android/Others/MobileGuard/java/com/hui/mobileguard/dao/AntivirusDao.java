package com.hui.mobileguard.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by HUI on 2016/6/10.
 */
public class AntivirusDao {

    private static final String TAG = "AntivirusDao";
    private final SQLiteDatabase db;

    public AntivirusDao() {
        db = SQLiteDatabase.openDatabase("data/data/com.hui.mobileguard/files/antivirus.db",
                null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * 检查当前的MD5是否在病毒库
     * @param md5
     * @return
     */
    public String checkFileVirus(String md5) {
        String desc = null;
//        Log.d(TAG, "checkFileVirus: " + db.getPath() + "|" + db.getPageSize());
        Cursor cursor = db.rawQuery("select desc from datable where md5 = ?", new String[]{md5});

        if(cursor.moveToNext()) {
            desc = cursor.getString(0);
        }

        cursor.close();
        return desc;
    }
}
