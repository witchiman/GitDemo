package com.hui.service_lifecycle;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by HUI on 2016/1/19.
 */
public class MyService extends Service {
    public static final String TAG = "MyService";
    MyIBinder binder = new MyIBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"destroy");
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.d(TAG,"bind service");
        return super.bindService(service, conn, flags);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "unbind service");
        return super.onUnbind(intent);
    }

    class MyIBinder extends Binder {

        public void startDownload() {
            Log.d(TAG,"start download");
        }

        public void completeDownload() {
            Log.d(TAG,"complete download");
        }
    }
}
