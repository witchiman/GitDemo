package com.hui.smstest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private TextView sender, content;
    private IntentFilter intentFilter;
    private MessageReceiver receiver;
    private EditText etTo, etContent;

    private IntentFilter sendFilter;
    private SendStatusReceiver statusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //接收短信
        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);

        receiver = new MessageReceiver();
        intentFilter = new IntentFilter();
        intentFilter.setPriority(100);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, intentFilter);


        //发送短信
        etTo = (EditText) findViewById(R.id.to);
        etContent = (EditText) findViewById(R.id.msg_input);


        //查看短信是否送达
        statusReceiver = new SendStatusReceiver();
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        registerReceiver(statusReceiver, sendFilter);


        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();

                Intent sendIntent = new Intent("SEND_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sendIntent, 0);
                smsManager.sendTextMessage(etTo.getText().toString(), null, etContent.getText().toString(),
                        pi, null);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(statusReceiver);
    }

    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus"); //提取短信信息
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], null);
            }
            String address = messages[0].getOriginatingAddress(); //获取发送方号码
            String fullMessage = "";
            for (SmsMessage message : messages) {
                fullMessage += message.getMessageBody(); //获取短信内容
            }

            content.setText(fullMessage);
            abortBroadcast();
        }
    }

    private class SendStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == RESULT_OK) {
                Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
