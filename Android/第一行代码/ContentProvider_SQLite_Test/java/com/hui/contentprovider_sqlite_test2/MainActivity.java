package com.hui.contentprovider_sqlite_test2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String PROVIDER_URI = "content://com.hui.contentprovider_sqlite_test.provider/book";
    private String newBookId;
    public static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(PROVIDER_URI);
                ContentValues cv = new ContentValues();
                cv.put("name", "Big cow story");
                cv.put("price", 22.5);
                Uri newUri = getContentResolver().insert(uri, cv);
                if(newUri !=null ){
                    Log.d(TAG,newUri.toString());
                }
                newBookId = newUri.getPathSegments().get(1);
                Log.d(TAG, "New book id is " + newBookId);
            }
        });

        findViewById(R.id.button_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(PROVIDER_URI);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d(TAG, "Book name is " + name);
                    Log.d(TAG, "Book price is " + price);
                }
                cursor.close();
            }
        });

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(PROVIDER_URI + "/" + newBookId);
                ContentValues cv = new ContentValues();
                cv.put("price", 11.25);
                getContentResolver().update(uri, cv, null, null);
                Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(PROVIDER_URI +"/" + newBookId);
                getContentResolver().delete(uri,null,null);
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
