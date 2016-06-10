package com.hui.service_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by HUI on 2016/1/19.
 *IntentService 自动开启线程和停止线程，比较适合执行一些耗时的逻辑
 */
public class MyIntentService extends IntentService {
    public static final String TAG = "IntentService";

    //要定义一个无参的构造方法，方法内调用父类一个参数的构造方法
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "开始");
        int count = 0 ;
        while(count < 10) {
            Log.d(TAG,(count++) + "\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "停止");
    }
}
