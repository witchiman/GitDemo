package org.software.hui.connectservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import static java.lang.Thread.sleep;

public class MyService extends Service {
    private  boolean running = false;
    private String data = "这是默认信息！";
    private Callback callback = null ;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallback() {
        return callback;
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  new Binder();
    }
    public class Binder extends android.os.Binder {
        public void setData(String data)  {
            MyService.this.data = data;
        }
        public MyService getService() {
            return  MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");  
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        running = true;

         new Thread(new Runnable() {
             int i= 0;
             @Override
             public void run() {
                 while(running) {
                     i++;
                     String str = i + ":"+ data;
                     if(callback != null) {
                         callback.onDataChange(str);
                     }
                     System.out.println(str);
                 }
                 try {
                     sleep(10000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }).start();
        System.out.println("creat~");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        System.out.println("destroy~");
        running = false;
        super.onDestroy();
    }


    public static interface Callback{
        void onDataChange(String data);
    }
}
