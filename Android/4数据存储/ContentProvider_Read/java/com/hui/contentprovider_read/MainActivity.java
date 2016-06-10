package com.hui.contentprovider_read;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Uri URI = Uri.parse("content://com.hui.cp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(URI, null, null, null, null);
        while(cursor.moveToNext()) {
            Toast.makeText(MainActivity.this, cursor.getString(cursor.getColumnIndex("name")), Toast.LENGTH_SHORT).show();
        }
    }


}
