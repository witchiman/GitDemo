package com.hui.mobileguard.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode;
import android.os.SystemClock;
import android.widget.ListView;

import com.hui.mobileguard.domain.BlackListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUI on 2016/5/21.
 */
public class BlackListDao {
    private BlackListDBHelper helper;

    public BlackListDao(Context context) {
        this.helper = new BlackListDBHelper(context);
    }

    public boolean add(String number, int mode) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number",number);
        values.put("mode",mode);
        long rowid = db.insert("blacklist", null, values);
        db.close();
        if (rowid != -1) {
            return true;
        } else {
            return false;
        }
    }

     public boolean delete(String number) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowid = db.delete("blacklist", "number=?", new String[]{number});
        db.close();
        if (rowid != -1) {
            return true;
        } else {
            return false;
        }
    }

    public  boolean changeMode(String number, int mode) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("mode", mode);
        int rowid = db.update("blacklist", values, "number=?", new String[]{number});
        db.close();
        if (rowid != -1) {
            return true;
        } else {
            return false;
        }
    }

    public int findMode(String number) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("blacklist", null, "number=?", new String[]{number}, null, null, null);
        int mode = 0;
        if (cursor.moveToNext()) {
            mode = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return mode;
    }

    public List<BlackListInfo> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("blacklist", new String[] {"number","mode"}, null, null, null, null, null);
        List<BlackListInfo> list = new ArrayList<BlackListInfo>();
        while (cursor.moveToNext()) {
            BlackListInfo info = new BlackListInfo();
            info.setNumber(cursor.getString(0));
            info.setMode(cursor.getInt(1));
            list.add(info);
        }
        cursor.close();
        db.close();
        SystemClock.sleep(3000);
        return list;
    }

    public boolean initDb() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowid = db.delete("blacklist", null, null);
        if (rowid != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 分页查找
     * @param pageSize 页面大小
     * @param currentPage 当前页
     * @return
     */
    public List<BlackListInfo> findPage(int pageSize, int currentPage) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from blacklist limit ? offset?", new String[]{
                String.valueOf(pageSize), String.valueOf(currentPage*pageSize)});

        List<BlackListInfo> list = new ArrayList<BlackListInfo>();
        while (cursor.moveToNext()) {
            BlackListInfo info = new BlackListInfo();
            info.setNumber(cursor.getString(0));
            info.setMode(cursor.getInt(1));
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getTotalNumber() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from blacklist", null);
        cursor.moveToNext();
        int number = cursor.getInt(0);
        cursor.close();
        db.close();
        return number;
    }
}
