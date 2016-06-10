package org.software.hui.learbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public static final  String ACTION = "org.software.hui.learbroadcastreceiver.intent.action.MyReceiver";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Receiver接到到了消息！"+ intent.getStringExtra("data"));
        //abortBroadcast();//执行此方法后后边优先级低的receiver便不会接收到消息了。
    }
}
