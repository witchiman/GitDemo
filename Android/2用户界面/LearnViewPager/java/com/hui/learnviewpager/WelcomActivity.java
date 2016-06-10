package com.hui.learnviewpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class WelcomActivity extends AppCompatActivity {
    public static final int GO_HOME = 1;
    public static final int GO_GUIDE = 2;
    public static final int TIME = 1000;
    private boolean isFirstIn = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        init();
    }

    private void init() {
        SharedPreferences perPreferences = getSharedPreferences("Hui", MODE_PRIVATE); //共享数据
        isFirstIn = perPreferences.getBoolean("isFirstIn", true);
        if(!isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            SharedPreferences.Editor editor = perPreferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        }
    }

    public void goHome() {
        Intent i = new Intent(WelcomActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void goGuide() {
        Intent i = new Intent(WelcomActivity.this,ViewPagerActivity.class);
        startActivity(i);
        finish();
    }

}
