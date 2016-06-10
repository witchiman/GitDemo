package com.hui.service_foreground;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;

/**
 * Created by HUI on 2016/1/19.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent pi  = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new Notification.Builder(getApplicationContext()).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("这是标题").
                setContentText("这是内容").
                setContentIntent(pi).build();
        startForeground(1,notification);
        Log.d("MyService", "服务开始运行");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService","停止服务");
    }
}
