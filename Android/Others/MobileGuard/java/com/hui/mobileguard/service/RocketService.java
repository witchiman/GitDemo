package com.hui.mobileguard.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.hui.mobileguard.R;
import com.hui.mobileguard.activity.RocketBGActivity;

/**
 * Created by HUI on 2016/5/18.
 */
public class RocketService extends Service {

    private static final String TAG = "RocketService";
    private WindowManager wWM;
    private WindowManager.LayoutParams params;
    private int winWidth;
    private int winHeight;
    private int startX;
    private int startY;
    private View view;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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


        winWidth = wWM.getDefaultDisplay().getWidth();
        winHeight = wWM.getDefaultDisplay().getHeight();

        view = View.inflate(this, R.layout.rocket, null);

        view.setBackgroundResource(R.drawable.anim_rocket);
        final AnimationDrawable anim = (AnimationDrawable) view.getBackground();

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        anim.start();    //开始动画
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
                        if (params.x >  winWidth- view.getWidth()) {
                            params.x = winWidth - view.getWidth();
                        }
                        if (params.y < 0) {
                            params.y = 0;
                        }
                        if (params.y  > winHeight - view.getHeight()) {
                            params.y = winHeight - view.getHeight();
                        }
                        wWM.updateViewLayout(view, params);

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "params " + params.x + "|" + params.y);
                        Log.d(TAG, "margin " + view.getLeft() + "|" + view.getTop());
                        if (params.x> (winWidth-view.getWidth())*1/2 && params.y
                                >(winHeight-view.getTop())*1/2) {
                            params.x = (winWidth-view.getWidth())/2;
                            params.y = winHeight;
                            wWM.updateViewLayout(view, params);// 发射时剧中显示

                            lauchRocket();   //发射火箭
                            Intent intent = new Intent(RocketService.this, RocketBGActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });

        wWM.addView(view, params);

    }

    private void lauchRocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            params.y -= winHeight/10;
            wWM.updateViewLayout(view, params);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wWM!=null && view!=null) {
            wWM.removeView(view);
            view = null;
        }
    }
}
