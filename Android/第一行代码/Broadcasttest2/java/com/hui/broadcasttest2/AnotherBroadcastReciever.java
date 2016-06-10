

package com.hui.broadcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AnotherBroadcastReciever extends BroadcastReceiver {
    public AnotherBroadcastReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "AnotherBroadcastReciever", Toast.LENGTH_SHORT).show();
    }
}
