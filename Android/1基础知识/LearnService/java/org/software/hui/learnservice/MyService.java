package org.software.hui.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.ContextThemeWrapper;

import static java.lang.Thread.sleep;

public class MyService extends Service {
    private boolean serviceRunning = false;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  new Binder();   //执行bindService()要修改此处代码
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start command!");
        return super.onStartCommand(intent, flags, startId);//执行startService()后执行此方法。
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(serviceRunning) {
                    System.out.println("服务正在运行");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        System.out.println("Service create!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceRunning = false;
        System.out.println("Service destroy! ");
    }
}
