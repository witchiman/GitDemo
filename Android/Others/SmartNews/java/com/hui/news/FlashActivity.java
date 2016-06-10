package com.hui.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.hui.news.utils.PrefUtils;

public class FlashActivity extends Activity {
    RelativeLayout flRoot;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        flRoot = (RelativeLayout) findViewById(R.id.flash_activity);
        startAnimation();
    }

    /**
    添加动画
    */
    private void startAnimation() {
        AnimationSet set = new AnimationSet(false);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setFillAfter(true);
        set.addAnimation(rotate);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);
        set.addAnimation(alpha);

        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(2000);
        scale.setFillAfter(true);
        set.addAnimation(scale);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        flRoot.startAnimation(set);

    }

    private void toNextPage() {
        boolean isFirst = PrefUtils.getBoolean(this, "is_first", true);
        if (isFirst) {
            startActivity(new Intent(FlashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(FlashActivity.this, MainActivity.class));
        }
        finish();
    }


}
