package com.hui.broadcast_forceoffline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HUI on 2016/1/16.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityColletion.addActiviy(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityColletion.removeActivity(this);
    }
}
