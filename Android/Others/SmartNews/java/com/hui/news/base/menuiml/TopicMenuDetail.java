package com.hui.news.base.menuiml;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hui.news.base.BaseMenuDetail;

/**
 * Created by HUI on 2016/4/11.
 */
public class TopicMenuDetail extends BaseMenuDetail {
    public TopicMenuDetail(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("专题");
        tv.setTextColor(Color.RED);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
