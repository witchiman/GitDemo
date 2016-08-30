package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hui.mobileguard.R;


/**
 * 第4个设置向导页
 * 
 * @author Kevin
 * 
 */
public class Setup4Activity extends BaseSetupActivity {

	private CheckBox cbProtect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		cbProtect = (CheckBox) findViewById(R.id.cb_protect);

		boolean protect = config.getBoolean("protect",false);
		if (protect) {
			cbProtect.setText("防盗保护已经开启");
			cbProtect.setChecked(true);
		} else {
			cbProtect.setText("防盗保护没有开启");
			cbProtect.setChecked(false);
		}

		cbProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cbProtect.setText("防盗保护已经开启");
					config.edit().putBoolean("protect", true).commit();
				} else {
					cbProtect.setText("防盗保护没有开启");
					config.edit().putBoolean("protect",false).commit();
				}
			}
		});

	}

	@Override
	public void showPrevious() {
		startActivity(new Intent(this, Setup3Activity.class));
		finish();

		overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
	}

	@Override
	public void showNext() {
		startActivity(new Intent(this, LostFindActivity.class));
		finish();
		config.edit().putBoolean("configed", true).commit();// 更新sp,表示已经展示过设置向导了,下次进来就不展示啦

		//两者界面切换动画的设置
		overridePendingTransition(R.anim.next_in, R.anim.next_out); //进入和退出的动画
	}

}
