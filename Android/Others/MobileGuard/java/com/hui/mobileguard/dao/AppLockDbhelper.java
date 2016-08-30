package com.hui.mobileguard.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HUI on 2016/6/12.
 */
public class AppLockDbhelper extends SQLiteOpenHelper {
    private final String CREATE_DB = "create table  appinfo (id integer primary key autoincrement, packagename " +
            "varchar(20))";

    public AppLockDbhelper(Context context) {
        super(context, "applock.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
