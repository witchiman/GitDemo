package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.hui.mobileguard.R;

public class ToolsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
    }

    public void queryNumber(View view) {
        startActivity(new Intent(this, AddressActivity.class));
    }

    public void appLock(View view) {
        startActivity(new Intent(this, AppLockActivity.class));
    }
}
