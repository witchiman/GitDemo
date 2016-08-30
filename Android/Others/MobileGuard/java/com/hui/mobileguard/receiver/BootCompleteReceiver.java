package com.hui.mobileguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by HUI on 2016/5/8.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences config = context.getSharedPreferences("CONFIG", Context.MODE_PRIVATE);
        boolean protect = config.getBoolean("protect", false);
        if (protect) {
            String sim = config.getString("sim", null);
            if (!TextUtils.isEmpty(sim)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String currentSim = tm.getSimSerialNumber() + "111";
                if (currentSim.equals(sim)) {
                    Log.d(TAG, "onReceive: 当前手机安全");
                } else {
                    Log.d(TAG, "onReceive: 发送报警短信");

                    String phone = config.getString("phone",null);  //获取安全号码
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone,null,"手机SIM卡已经更换",null,null);
                }
            }
        }
    }
}
