package com.hui.mobileguard.view;

import android.content.Context;
import android.media.MediaDescription;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hui.mobileguard.R;

/**
 * 自定义组合控件
 * Created by HUI on 2016/5/2.
 */
public class SettingItemView extends RelativeLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private static final String TAG = "SettingItemView";
    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbStatus;
    private String mTitle;
    private String mDescOn;
    private String mDescOff;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTitle = attrs.getAttributeValue(NAMESPACE, "setting_title");
        mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTitle = attrs.getAttributeValue(NAMESPACE, "setting_title");
        mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");

        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbStatus = (CheckBox) findViewById(R.id.cb_status);
        cbStatus.setClickable(false);

        setTitle(mTitle);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesciption(String desciption) {
        tvDesc.setText(desciption);
    }

    public boolean isChecked() {
        return cbStatus.isChecked();
    }

    public void setStatus(boolean status) {
        cbStatus.setChecked(status);
        if (status) {
            setDesciption(mDescOn);
        } else {
            setDesciption(mDescOff);
        }
    }

}
