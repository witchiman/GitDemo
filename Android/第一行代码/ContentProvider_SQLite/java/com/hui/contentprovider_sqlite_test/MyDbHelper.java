package com.hui.contentprovider_sqlite_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HUI on 2016/1/18.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    public static String CREATE_BOOK = "create table book(" +
                                        "id integer primary key autoincrement," +
                                        "name text," +
                                        "price real)";
    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        //db.execSQL("CREAT_OTHER");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion) {
            /*保证跨版本升级时每一步都能执行到*/
            case 1:
                //db.execSQL(CREATE_OTHER);
            case 2:
                //db.execSQL("insert into table Book add column other integer");
            default:
                break;
        }
    }
}
