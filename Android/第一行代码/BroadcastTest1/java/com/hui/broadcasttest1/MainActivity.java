package com.hui.broadcasttest1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*本App使用了标准广播，有序广播和BootComplete广播*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.hui.broadcasttest1.MY_BROADCAST");
                //sendBroadcast(intent);
                sendOrderedBroadcast(intent,null);
            }

        });
    }


}
