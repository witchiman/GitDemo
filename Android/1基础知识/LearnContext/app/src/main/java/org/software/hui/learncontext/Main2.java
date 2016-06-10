package org.software.hui.learncontext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wangj on 2015/10/9.
 */
public class Main2 extends Activity {
    private TextView tv;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        tv = (TextView) findViewById(R.id.textView);
        et = (EditText) findViewById(R.id.editText);
        tv.setText("共享的数据是：" + getApp().getStr().toString());
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App)getApplicationContext()).setStr(et.getText().toString());
                tv.setText("共享的数据是：" + et.getText().toString());
            }
        });
    }
    public App getApp() {
        return (App)getApplicationContext();
    }
}
