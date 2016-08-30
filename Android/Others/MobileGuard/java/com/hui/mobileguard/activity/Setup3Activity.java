package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hui.mobileguard.R;

/**
 * 第3个设置向导页
 * 
 * @author Kevin
 * 
 */
public class Setup3Activity extends BaseSetupActivity {
	private EditText etPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		etPhone = (EditText) findViewById(R.id.et_phone);
		String phone = config.getString("phone","");
		etPhone.setText(phone);
	}

	@Override
	public void showPrevious() {
		startActivity(new Intent(this, Setup2Activity.class));
		finish();

		overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
	}

	@Override
	public void showNext() {
		String phone = etPhone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(Setup3Activity.this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		config.edit().putString("phone", phone).commit();

		startActivity(new Intent(this, Setup4Activity.class));
		finish();
		//两者界面切换动画的设置
		overridePendingTransition(R.anim.next_in, R.anim.next_out); //进入和退出的动画
	}

	public void chooseContacts(View view) {
		Intent intent = new Intent(this, ContactActivity.class);
		startActivityForResult(intent,1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == RESULT_OK) {
			 String phone = data.getStringExtra("phone").replace(" ","");
			 etPhone.setText(phone);
		 }
	}
}
