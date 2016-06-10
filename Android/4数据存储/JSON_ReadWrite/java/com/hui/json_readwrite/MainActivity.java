package com.hui.json_readwrite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textview);

        /*解析JSON文件*/
      /*  try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("data.json"));
            BufferedReader br = new BufferedReader(isr);
			br.close();
			isr.close();
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!=null) {
                sb.append(line);
            }

            JSONObject root  = new JSONObject(sb.toString());
            tv.append("Category is:"+root.getString("category")+"\n");
            JSONArray array = root.getJSONArray("languages");
            for(int i=0; i<array.length(); i++) {
                JSONObject lan  = array.getJSONObject(i);
                tv.append("--------"+"\n");
                tv.append(lan.getInt("id")+"\n");
                tv.append(lan.getString("ide")+"\n");
                tv.append(lan.getString("name")+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        /*生成JSON文件*/
        try {
            JSONObject root = new JSONObject();

            JSONObject lan1 = new JSONObject();
            lan1.put("id",1);
            lan1.put("ide","Eclipse");
            lan1.put("name","Java");
            JSONObject lan2 = new JSONObject();
            lan2.put("id",2);
            lan2.put("ide","Visual Studio");
            lan2.put("name","C#");
            JSONObject lan3 = new JSONObject();
            lan3.put("id",3);
            lan3.put("ide","XCode");
            lan3.put("name","Swift");

            JSONArray array = new JSONArray();
            array.put(lan1);
            array.put(lan2);
            array.put(lan3);
            root.put("languages", array);
            root.put("category","IT");

            Log.d("MainActivity",root.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
