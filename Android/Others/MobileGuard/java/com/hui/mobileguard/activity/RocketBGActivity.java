package com.hui.mobileguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.hui.mobileguard.R;

/**
 * Created by HUI on 2016/5/18.
 */
public class RocketBGActivity extends Activity {

    private ImageView ivSmokeUp;
    private ImageView ivSmokeDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_bg);

        ivSmokeUp = (ImageView) findViewById(R.id.iv_rocket_up);
        ivSmokeDown = (ImageView) findViewById(R.id.iv_rocket_down);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);  //加入渐变动画
        alpha.setDuration(1000);
        alpha.setFillAfter(true);
        ivSmokeUp.startAnimation(alpha);
        ivSmokeDown.startAnimation(alpha);

        new Handler().postDelayed(new Runnable() { //设置1s后结束
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
