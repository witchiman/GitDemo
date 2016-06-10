package com.hui.news.base.pageriml;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hui.news.base.BasePager;

/**
 * 设置实现
 * Created by HUI on 2016/4/9.
 */
public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("设置");
        TextView tv = new TextView(mActivity);
        tv.setText("设置");
        tv.setTextColor(Color.RED);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        flContent.addView(tv);
        setSlidingMenuEnable(false);
        ivMenu.setVisibility(View.GONE); //设置标题栏菜单按钮不可见
    }
}
