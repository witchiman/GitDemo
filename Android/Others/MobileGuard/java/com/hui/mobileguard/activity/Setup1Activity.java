package com.hui.mobileguard.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hui.mobileguard.R;

public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showPrevious() {

    }

    @Override
    public void showNext() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        //两者界面切换动画的设置
        overridePendingTransition(R.anim.next_in, R.anim.next_out); //进入和退出的动画
    }

}
