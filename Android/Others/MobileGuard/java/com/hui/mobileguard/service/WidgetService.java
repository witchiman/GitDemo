package com.hui.mobileguard.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.hui.mobileguard.R;
import com.hui.mobileguard.receiver.WidgetProvider;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by HUI on 2016/6/10.
 */
public class WidgetService extends Service {

    private static final String TAG = "WidgetService";
    private AppWidgetManager widgetManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 服务运行了？");
        widgetManager = AppWidgetManager.getInstance(this);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int count = 0;
            @Override
            public void run() {

                ComponentName provider = new ComponentName(getApplicationContext(), WidgetProvider.class);

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
                views.setTextViewText(R.id.tv_process_count, count++ + "");
                views.setTextViewText(R.id.tv_process_memory, "反正很大");
                /*设置监听事件*/
                Intent intent = new Intent();
                intent.setAction("com.hui.mobileguard.software");
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                views.setOnClickPendingIntent(R.id.btn_clear,pendingIntent );

                widgetManager.updateAppWidget(provider, views);
            }
        };

        timer.schedule(task,0, 5000);
    }
}
