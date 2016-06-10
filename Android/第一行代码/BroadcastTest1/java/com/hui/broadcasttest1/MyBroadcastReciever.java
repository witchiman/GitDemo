package com.hui.broadcasttest1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver {
    public MyBroadcastReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "MyBroadcastReciever", Toast.LENGTH_SHORT).show();
        abortBroadcast();  //有序广播里阻断优先级别低的reciever
    }
}
