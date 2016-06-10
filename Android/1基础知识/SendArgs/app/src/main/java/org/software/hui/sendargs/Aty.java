package org.software.hui.sendargs;

import android.content.Intent;
import android.support.v4.media.MediaDescriptionCompatApi21;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Aty extends AppCompatActivity {
    private TextView tv;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty);
        Intent i = getIntent();
        //Bundle b = i.getExtras();
        Bundle b = i.getBundleExtra("data");
        tv = (TextView) findViewById(R.id.tv);
        editText = (EditText) findViewById(R.id.editText);

        //获取一个字符串
        //tv.setText(i.getStringExtra("data"));
        //获取一个Bundle
       /* tv.setText(String.format("data1: %s,data2:%d,data3:%s",b.getString("data1"),
                b.getInt("data2"), b.getString("data3","DefaultValue")));*/

        //获取一个自定义的User，User实现了Serializable接口
        //User u = (User)i.getSerializableExtra("user");
        //获取一个自定义的User,User实现了Parcelable接口
        User u = i.getParcelableExtra("user");
        tv.setText(String.format("User name:%s,age:%d",u.getName(), u.getAge()));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("data", editText.getText().toString());
                setResult(1, i);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aty, menu);
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
