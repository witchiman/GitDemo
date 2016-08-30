package com.hui.mobileguard.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUI on 2016/6/12.
 */
public class AppLockDao {

    private final AppLockDbhelper dbhelper;

    public AppLockDao(Context context) {
        dbhelper = new AppLockDbhelper(context);
    }

    public void add(String packageName) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("packagename", packageName);
        db.insert("appinfo",null,contentValues );
        db.close();
    }

    public boolean find(String packageName) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        boolean result = false;
        Cursor cursor = db.query("appinfo", null, "packagename=?", new String[]{packageName}, null, null, null);
        if (cursor.moveToNext()) {
            result  = true;
        }

        cursor.close();
        db.close();
        return result;
    }

    public List<String> findAll() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        List<String> list = new ArrayList<String>();

        Cursor cursor = db.query("appinfo",new String[] {"packagename"},null, null, null, null,
                null);

        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return list;
    }

    public void delete(String packageName) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete("appinfo","packagename=?",new String[]{packageName});
        db.close();
    }
}
