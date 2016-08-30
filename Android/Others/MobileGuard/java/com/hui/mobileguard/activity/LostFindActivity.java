package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.mobileguard.R;

public class LostFindActivity extends Activity{
    private static final String TAG = "LostFindActivity";
    private TextView tvSavePhone;
    private ImageView ivLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences config = getSharedPreferences("CONFIG", MODE_PRIVATE);
        boolean configed = config.getBoolean("configed", false);
        if (configed) {
            setContentView(R.layout.activity_lost_find);
            tvSavePhone = (TextView) findViewById(R.id.tv_safe_phone);
            ivLock = (ImageView) findViewById(R.id.iv_lock);

            String safePhone = config.getString("phone","");
            tvSavePhone.setText(safePhone);

            boolean protect = config.getBoolean("protect",false);
            if (protect) {
                ivLock.setImageResource(R.drawable.lock);
            } else {
                ivLock.setImageResource(R.drawable.unlock);
            }
        } else {
            /*跳转至向导页面*/
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
    }

    public void resetGuide(View view) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}
