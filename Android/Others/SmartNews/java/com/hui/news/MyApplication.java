package com.hui.news;

import android.app.Application;

import org.xutils.x;

/**
 * Created by HUI on 2016/4/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
