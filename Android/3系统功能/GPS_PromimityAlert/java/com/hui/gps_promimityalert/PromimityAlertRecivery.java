package com.hui.gps_promimityalert;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class PromimityAlertRecivery {
	public void onReceive(Context context, Intent intent) {
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		
		if(isEnter)
		{
			Toast.makeText(context, "已经进入", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(context, "已经离开", Toast.LENGTH_LONG).show();
		}

	}
}
