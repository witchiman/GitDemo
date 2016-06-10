package com.hui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_notify).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_notify:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img);
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification = new Notification.Builder(this).
                        setContentIntent(pi).
                        setContentTitle("It's a title").
                        setContentText("Here's the content").
                        setLargeIcon(bitmap).
                        setSmallIcon(R.mipmap.ic_launcher).
                        build();
                notification.defaults = Notification.DEFAULT_ALL;
                manager.notify(1,notification);
                break;
            default:
                break;
        }
    }
}
