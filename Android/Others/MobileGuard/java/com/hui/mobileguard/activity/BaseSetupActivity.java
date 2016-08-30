package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HUI on 2016/5/8.
 */
public abstract class BaseSetupActivity extends Activity {
    private GestureDetector detector;
    public SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = getSharedPreferences("CONFIG", MODE_PRIVATE);

        detector  = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener() {//监听滑动事件
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(e1.getRawX()-e2.getRawX()) <100) {
                    return true;
                }

                if (e2.getRawX()-e1.getRawX() > 200) { //向右滑
                    showPrevious();
                    return true;
                }

                if (e1.getRawX()-e2.getRawX() > 200) {
                    showNext();
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public abstract void showPrevious();

    public abstract void showNext();

    public void previous(View view) {
        showPrevious();
    }

    public void next(View view) {
        showNext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
