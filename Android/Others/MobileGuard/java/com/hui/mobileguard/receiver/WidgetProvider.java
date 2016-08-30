package com.hui.mobileguard.receiver;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hui.mobileguard.service.WidgetService;

/**
 * Created by HUI on 2016/6/10.
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetProvider";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled: 部件启动");
        context.startService(new Intent(context, WidgetService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisabled: 部件销毁");
        context.stopService(new Intent(context, WidgetService.class));
    }
}
