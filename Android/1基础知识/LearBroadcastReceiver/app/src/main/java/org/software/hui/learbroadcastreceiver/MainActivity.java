package org.software.hui.learbroadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyReceiver receiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnSendMsg).setOnClickListener(this);
        findViewById(R.id.btnReg).setOnClickListener(this);
        findViewById(R.id.btnUnReg).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSendMsg:
                //Intent i = new Intent(this, MyReceiver.class);
                Intent i = new Intent(MyReceiver.ACTION);//注册BroadcastReceiver
                i.putExtra("data","一个蛋疼的数据！");
                sendBroadcast(i);
                //sendOrderedBroadcast(i);//如果想中断优先级比较代的Receiver必须使用此方法。
                break;
            case R.id.btnReg:
                if(receiver == null) {
                    receiver = new MyReceiver();
                    registerReceiver(receiver, new IntentFilter(MyReceiver.ACTION));
                }
                break;
            case R.id.btnUnReg:
                if(receiver != null) {
                    unregisterReceiver(receiver);
                    receiver = null;
                }
                break;
        }
    }
}
