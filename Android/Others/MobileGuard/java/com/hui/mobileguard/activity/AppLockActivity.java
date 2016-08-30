package com.hui.mobileguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hui.mobileguard.R;
import com.hui.mobileguard.fragment.AppUnlockFragment;
import com.hui.mobileguard.fragment.AppLockFragment;

public class AppLockActivity extends Activity implements View.OnClickListener {

    private AppUnlockFragment unlockFragment;
    private AppLockFragment lockFragment;
    private Button btnUnlcok;
    private Button btnLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);

        unlockFragment = new AppUnlockFragment();
        lockFragment = new AppLockFragment();

        btnUnlcok = (Button) findViewById(R.id.btn_unlock);
        btnUnlcok.setOnClickListener(this);

        btnLock = (Button) findViewById(R.id.btn_lock);
        btnLock.setOnClickListener(this);
        getFragmentManager().beginTransaction().replace(R.id.fl_content, unlockFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_unlock:
                getFragmentManager().beginTransaction().replace(R.id.fl_content, unlockFragment)
                    .commit();
                btnUnlcok.setBackgroundResource(R.drawable.tab_left_pressed);
                btnLock.setBackgroundResource(R.drawable.tab_right_default);
                break;
            case R.id.btn_lock:
                getFragmentManager().beginTransaction().replace(R.id.fl_content, lockFragment)
                    .commit();
                btnUnlcok.setBackgroundResource(R.drawable.tab_left_default);
                btnLock.setBackgroundResource(R.drawable.tab_right_pressed);
                break;
            default:
                break;
        }
    }
}
