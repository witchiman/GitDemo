package org.software.hui.learnservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
  private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this); // Activity实现View.ConClickListener后可简化
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        i = new Intent(MainActivity.this, MyService.class);
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
    //实现View.OnClickListener后要实现的方法
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnStartService:
                startService(i);
                break;
            case R.id.btnStopService:
                stopService(i);
                break;
            case R.id.btnBindService:  //MainActivity要实现ServiceConnection接口
                bindService(i, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                unbindService(this);
                break;

        }
    }
    // 以下两个方法为实现ServiceConnection接口后要实现的方法
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Service connected!");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("Service disconnected!");
    }
}
