package com.hui.mobileguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.hui.mobileguard.R;
import com.hui.mobileguard.service.LocationService;


/**
 * Created by HUI on 2016/5/8.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        String format = intent.getExtras().getString("format");
        for (Object object : objects) {
            Log.d(TAG, "onReceive: fuck it");
            SmsMessage message = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                message = SmsMessage.createFromPdu((byte[]) object, format);
            } else {
                message = SmsMessage.createFromPdu((byte[]) object);
            }
            String address = message.getDisplayOriginatingAddress();
            String messageBody = message.getDisplayMessageBody();
            Log.d(TAG, "onReceive: sms" + messageBody);
            if (messageBody.equals("#*alarm*#")) {
                Log.d(TAG, "onReceive: alarm");
                MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
                player.setVolume(1f, 1f);
                player.setLooping(true);
                player.start();
                Toast.makeText(context, "中断", Toast.LENGTH_SHORT).show();
                abortBroadcast();     //中断短信的传递,不管用，4.x系统需要实现成系统默认短信应用
            }else if (messageBody.equals("#*location*#")) {
                Log.d(TAG, "onReceive: here");
                context.startService(new Intent(context, LocationService.class));
                SharedPreferences config = context.getSharedPreferences("CONFIG", context.MODE_PRIVATE);
                String location = config.getString("location", "");
                Log.d(TAG, "location: " + location);
                abortBroadcast();
            }
        }

    }
}
