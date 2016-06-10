package com.hui.httpget;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new AsyncTask<String, Void, Void>() {
                     @Override
                     protected Void doInBackground(String... params) {
                         try {
                             URL url = new URL(params[0]);
                             URLConnection connection = url.openConnection();
                             InputStream is = connection.getInputStream();
                             InputStreamReader isr = new InputStreamReader(is);
                             BufferedReader br = new BufferedReader(isr);
                             String line;
                             while((line=br.readLine())!=null) {
                                 Log.d("MainActivity",line);
                             }
                             br.close();
                             isr.close();
                             is.close();
                         } catch (MalformedURLException e) {
                             e.printStackTrace();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         return null;
                     }
                 }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=huisoft&key=1261218598&type=data&doctype=xml&version=1.1&q=good");
             }
         });
    }


}
