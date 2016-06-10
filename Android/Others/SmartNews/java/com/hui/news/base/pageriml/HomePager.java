package com.hui.news.base.pageriml;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hui.news.base.BasePager;

/**
 * 首页实现
 * Created by HUI on 2016/4/9.
 */
public class HomePager extends BasePager {
    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("主页");
        TextView tv = new TextView(mActivity);
        tv.setText("主页");
        tv.setTextColor(Color.RED);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        flContent.addView(tv);
        setSlidingMenuEnable(false);       //设置侧边栏不可用
        ivMenu.setVisibility(View.GONE); //设置标题栏菜单按钮不可见
    }
}
