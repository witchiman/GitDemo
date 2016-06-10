package org.software.hui.learncontext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main1 extends AppCompatActivity {
   private TextView tv;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainActivity onCreate!");
        /*tv = new TextView(this);
        //tv.setText("Hello");
        tv.setText(R.string.hello_world);
        setContentView(tv);
        System.out.println(getResources().getText(R.string.hello_world));*/

        /*ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher);
        setContentView(iv);*/
        setContentView(R.layout.main1);
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
}
