package com.hui.ipc_messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HUI on 2016/6/2.
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    private Messenger mMessenger = new Messenger(new MessengerHandler());

    /*自定义一个Handler用于处理客户端进程的请求*/
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.FROM_CLIENT:
                    Log.d(TAG, "服务器收到的信息：" + msg.getData().getString("msg"));

                    /*响应客户端*/
                    Messenger client = msg.replyTo;
                    Message message = Message.obtain(null, MyConstants.FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("response","服务器已经收到你的信息了");
                    message.setData(bundle);

                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 已经销毁了");
    }
}
