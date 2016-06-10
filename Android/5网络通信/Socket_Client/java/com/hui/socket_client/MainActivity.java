package com.hui.socket_client;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2;
    private TextView tv;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.edit1);
        et2= (EditText) findViewById(R.id.edit2);
        tv = (TextView) findViewById(R.id.textview);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip = et1.getText().toString();
                connect();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    Socket socket = null;
    BufferedWriter bw = null;
    BufferedReader br = null;

    public void connect() {
       AsyncTask<Void, String, Void> read =  new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    socket = new Socket(ip,6780);
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    publishProgress("Connect successfully!");
                    String line;
                    while((line=br.readLine())!=null) {
                        publishProgress(line);
                    }

                }catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                System.out.println(values[0]);
                if(values[0].equals("Connect successfully!")) {
                    Toast.makeText(MainActivity.this, "已经成功连接！", Toast.LENGTH_SHORT).show();
                }else {
                    tv.append(values[0]);
                }
                super.onProgressUpdate(values);
            }
        };
        read.execute();
    }

    public void send() {
        try {
            if(et2.getText() == null) {
                Toast.makeText(MainActivity.this, "请输入一些内容！", Toast.LENGTH_SHORT).show();
                return;
            }
            tv.append("我：" +et2.getText().toString()+"\n" );
            bw.write(et2.getText().toString()+"\n");
            bw.flush();
            et2.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
