package com.hui.mobileguard;

import android.app.Application;

import org.xutils.x;

/**
 * Created by HUI on 2016/4/29.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
