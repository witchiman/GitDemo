package org.software.hui.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.software.hui.startservicefromanotherapp.IAppServiceBinder;

public class MainActivity extends AppCompatActivity implements ServiceConnection, View.OnClickListener {
    private Intent intentService;
    private EditText et;
    private IAppServiceBinder binder = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
        intentService = new Intent();
        intentService.setComponent(new ComponentName("org.software.hui.startservicefromanotherapp",
                "org.software.hui.startservicefromanotherapp.AppService"));
        et = (EditText)findViewById(R.id.editText);
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
        switch (v.getId()) {
            case R.id.btnStartService:
                startService(intentService);
                break;
            case R.id.btnStopService:
                stopService(intentService);
                break;
            case R.id.btnBindService:
                bindService(intentService, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                unbindService(this);
                break;
            case R.id.btnSync:
                if(binder != null) {
                    try {
                        binder.setData(et.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }



    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Service conected!");
        System.out.println(service);
        binder = IAppServiceBinder.Stub.asInterface(service);//不能使用强制转换，因为IAppServiceBinder
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
