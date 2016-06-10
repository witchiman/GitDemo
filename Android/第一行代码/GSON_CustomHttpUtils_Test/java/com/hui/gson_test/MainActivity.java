package com.hui.gson_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private String response;
    public static final int SHOW_RESPONSE = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
             switch (msg.what) {
                 case SHOW_RESPONSE:
                     response = (String) msg.obj;
                     parseWithGSON(response);
                     break;
                 default:
                     break;
             }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text_response);

        String url = "http://192.168.215.168/persons.json";
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message msg = new Message();
                msg.what = SHOW_RESPONSE;
                msg.obj = response;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void parseWithGSON(String res) {
        Gson gson = new Gson();
        List<Person> persons = gson.fromJson(res,new TypeToken<List<Person>>(){}.getType()); //数组的话需要借助TypeToken，不然的话直接传入Person.class即可
        for (Person person : persons) {
            tv.append("name is " + person.getName()+"\n");
            tv.append("sex is " + person.getSex() + "\n");
            tv.append("age is " + person.getAge());
        }
    }

}
