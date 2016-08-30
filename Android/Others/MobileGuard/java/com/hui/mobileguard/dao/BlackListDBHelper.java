package com.hui.mobileguard.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HUI on 2016/5/21.
 */
public class BlackListDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE = "create table blacklist (_id integer primary key " +
            "autoincrement,number varchar(20), mode int)";

    public BlackListDBHelper(Context context) {
        super(context, "callsafe.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
