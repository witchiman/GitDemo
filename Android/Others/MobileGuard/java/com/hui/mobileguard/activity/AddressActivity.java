package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.hui.mobileguard.R;
import com.hui.mobileguard.dao.AddressDao;

/**
 * Created by HUI on 2016/5/12.
 */
public class AddressActivity extends Activity {
    private EditText etQuery;
    private TextView tvResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        etQuery = (EditText) findViewById(R.id.et_query);
        tvResult = (TextView) findViewById(R.id.tv_result);
        etQuery.addTextChangedListener(new TextWatcher() {//监听输入的数据
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = AddressDao.getAddress(s.toString());
                tvResult.setText(address);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void query(View view) {
        String number = etQuery.getText().toString().trim();
        if (!TextUtils.isEmpty(number)) {
            String address = AddressDao.getAddress(number);
            tvResult.setText(address);
        } else {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
           /* shake.setInterpolator(new Interpolator() {  //也可以添加自定义的插补器
                @Override
                public float getInterpolation(float input) {
                    float output = 0;
                    output = (float) Math.sin(2*Math.PI*input);
                    return output;
                }
            });*/
            etQuery.startAnimation(shake);
            vibrate();
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(2000);   //震动2s
        vibrator.vibrate(new long[] {1000, 2000,2000,3000}, -1);  //第一个long数组先等待1s再震动2s，然后等待2s再震动3s
                                                                 //第二个参数-1代表不循环，0表示从头儿开始循环
        //vibrator.cancel();   //取消震动
    }
}
