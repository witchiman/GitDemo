package com.hui.broadcast_forceoffline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by HUI on 2016/1/16.
 */
public class Welcome extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        findViewById(R.id.button_offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.hui.broadcast.forceoffline.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
