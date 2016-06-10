package com.hui.contentwriter;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("name","Jave");
                getContentResolver().insert(MyContentProvider.URI, cv);
                cv.put("name", "C#");
                getContentResolver().insert(MyContentProvider.URI, cv);
                cv.put("name", "C");
                getContentResolver().insert(MyContentProvider.URI, cv);
                cv.put("name","C++");
                getContentResolver().insert(MyContentProvider.URI,cv);
            }
        });
    }


}
