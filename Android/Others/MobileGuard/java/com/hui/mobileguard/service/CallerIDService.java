package com.hui.mobileguard.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.mobileguard.R;
import com.hui.mobileguard.dao.AddressDao;

import java.sql.ParameterMetaData;

/**
 * 实现来电归属地显示
 * Created by HUI on 2016/5/14.
 */
public class CallerIDService extends Service {
    private static final String TAG = "CallerIDService";
    private TelephonyManager tm;
    private MyPhoneListener listener;
    private OutCallReceiver receiver;
    private WindowManager wWM;
    private View view;
    private SharedPreferences config;
    private int startX;
    private int startY;
    private WindowManager.LayoutParams params;
    private int winX;
    private int winY;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyPhoneListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        config = getSharedPreferences("CONFIG", MODE_PRIVATE);
        receiver = new OutCallReceiver();  //
        IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(receiver, filter);
    }

    class MyPhoneListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    String address = AddressDao.getAddress(incomingNumber);
                    //Toast.makeText(CallerIDService.this, address+"来电", Toast.LENGTH_LONG).show();
                    showAddressToast(address);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:  //电话闲置状态
                    if (wWM != null && view!=null) {
                        wWM.removeView(view);
                        view = null;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);   //停止来电的监听
        unregisterReceiver(receiver);
    }

    /**
     * 监听去电的广播接受者 需要权限: android.permission.PROCESS_OUTGOING_CALLS
     服务内建新一个内部广播类，保证与该Service一起创建和销毁
     */
    class OutCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = getResultData();
            String address = AddressDao.getAddress(number);

            showAddressToast(address);
        }
    }

    /**
      *显示自定义的Toast
     *自定义归属地浮窗 需要权限android.permission.SYSTEM_ALERT_WINDOW
     * */
    private void showAddressToast(String content) {
        wWM = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;

        params.type = WindowManager.LayoutParams.TYPE_TOAST;// 电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下
        params.gravity = Gravity.LEFT + Gravity.TOP;   //重新设置初始基准坐标为左上

        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        int lastX  = config.getInt("lastX", 0);
        int lastY = config.getInt("lastY", 0);
        params.x = lastX;
        params.y = lastY;

        winX = wWM.getDefaultDisplay().getWidth();
        winY = wWM.getDefaultDisplay().getHeight();

        view = View.inflate(this, R.layout.toast_address, null);

        int[] items = new int[] {R.drawable.call_locate_white, R.drawable.call_locate_orange,
            R.drawable.call_locate_blue,R.drawable.call_locate_gray,
            R.drawable.call_locate_green};
        int style  = config.getInt("style", 0);
        view.setBackgroundResource(items[style]);   //根据设置项设置来电提示框背景

        TextView tvAddress = (TextView) view.findViewById(R.id.tv_address);
        tvAddress.setText(content);
        tvAddress.setTextColor(Color.CYAN);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawY();
                        int dx = endX - startX;
                        int dy = endY - startY;
                        params.x += dx;
                        params.y += dy;

                        if (params.x < 0) {
                            params.x = 0;
                        }
                        if (params.x > winX - view.getWidth()) {
                            params.x = winX - view.getWidth();
                        }
                        if (params.y < 0) {
                            params.y = 0;
                        }
                        if (params.y  > winY - view.getHeight()) {
                            params.y = winY - view.getHeight();
                        }
                        wWM.updateViewLayout(view, params);

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        config.edit().putInt("lastX", params.x).putInt("lastY", params.y)
                                .commit();
                        break;
                }
                return true;
            }
        });

        wWM.addView(view, params);

    }
}
