package com.hui.contentprovider_sqlite_test;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by HUI on 2016/1/18.
 */
public class MyContentProvider extends ContentProvider {
    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static UriMatcher uriMatcher;
    private MyDbHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.hui.contentprovider_sqlite_test.provider", "book",BOOK_DIR);
        uriMatcher.addURI("com.hui.contentprovider_sqlite_test.provider","book/#", BOOK_ITEM);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new MyDbHelper(getContext(),"db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch(uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("book", projection, selection, selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("book",projection,"id=?",new String[]{bookId},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.hui.contentprovider_sqlite_test.provider/book";  //返回MIME字符串
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.hui.contentprovider_sqlite_test.provider/book";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriRetrun = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("book",null,values);
                uriRetrun = Uri.parse("content://com.hui.contentprovider_sqlite_test.provider/book/" + newBookId);
                break;
            default:
                break;
        }
        return uriRetrun;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                rows = db.delete("book",selection, selectionArgs);
            case BOOK_ITEM:
               String bookId = uri.getPathSegments().get(1);
                rows = db.delete("book","id=?",new String[]{bookId});
                break;
            default:
                break;
        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                rows = db.update("book",values,selection,selectionArgs);
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                rows = db.update("book",values,"id=?",new String[] {bookId});
        }
        return rows;
    }
}
