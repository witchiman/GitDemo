package com.hui.mobileguard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hui.mobileguard.R;

/**
 * 自定义组合控件
 * Created by HUI on 2016/5/2.
 */
public class SettingItemClick extends RelativeLayout {

    private static final String TAG = "SettingClickView";
    private TextView tvTitle;
    private TextView tvDesc;
    public SettingItemClick(Context context) {
        super(context);
        initView();
    }

    public SettingItemClick(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingItemClick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_setting_click, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesciption(String desciption) {
        tvDesc.setText(desciption);
    }

}
