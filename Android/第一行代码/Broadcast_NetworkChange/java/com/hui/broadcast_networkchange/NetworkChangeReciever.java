package com.hui.broadcast_networkchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by HUI on 2016/1/15.
 */
public class NetworkChangeReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info !=null && info.isAvailable()) {
            Toast.makeText(context, "网络正常", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
        }
    }
}
