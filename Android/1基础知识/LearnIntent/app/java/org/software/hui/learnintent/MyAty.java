package org.software.hui.learnintent;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by wangj on 2015/10/7.
 */
public class MyAty extends Activity {
    public static final String ACTION = "org.software.hui.learnintent.MyAty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }
}
