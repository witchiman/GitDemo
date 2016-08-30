package com.hui.mobileguard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hui.mobileguard.R;
import com.hui.mobileguard.view.SettingItemView;

/**
 * 第2个设置向导页
 * 
 * @author Kevin
 * 
 */
public class Setup2Activity extends BaseSetupActivity {

	private static final String TAG = "Setup2Activity";
	private SettingItemView sivSim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		sivSim = (SettingItemView) findViewById(R.id.siv_sim);

		String simInfo = config.getString("sim", null);
		if (!TextUtils.isEmpty(simInfo)) {
			sivSim.setStatus(true);
		} else {
			sivSim.setStatus(false);
		}
		sivSim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!sivSim.isChecked()) {
					sivSim.setStatus(true);
					//获取SIM卡序列号
					TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = tm.getSimSerialNumber();
					Log.d(TAG, "simSerialNumber: " + simSerialNumber);
					config.edit().putString("sim",simSerialNumber).commit();
				} else {
					sivSim.setStatus(false);
					config.edit().remove("sim").commit();  //移除序列号
				}
			}
		});
	}

	@Override
	public void showPrevious() {
		startActivity(new Intent(this, Setup1Activity.class));
		finish();

		overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
	}

	@Override
	public void showNext() {
		String sim = config.getString("sim", null);
		if (TextUtils.isEmpty(sim)) {
			Toast.makeText(Setup2Activity.this, "没有绑定SIM卡", Toast.LENGTH_SHORT).show();
			return;
		}
		startActivity(new Intent(this, Setup3Activity.class));
		finish();
		//两者界面切换动画的设置
		overridePendingTransition(R.anim.next_in, R.anim.next_out); //进入和退出的动画
	}

}
