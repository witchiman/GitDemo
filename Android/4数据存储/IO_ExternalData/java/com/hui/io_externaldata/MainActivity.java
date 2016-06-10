package com.hui.io_externaldata;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText et;
    private Button btn1;
    private Button btn2;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);
        final File sdCard = Environment.getExternalStorageDirectory();
        final File myFile = new File(sdCard, "file.txt");

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sdCard.exists()) {
                    Toast.makeText(getApplicationContext(),"SD卡不存在！",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(!myFile.exists()) {
                        try {
                            myFile.createNewFile();
                            Toast.makeText(getApplicationContext(),"文件已创建！",Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            FileOutputStream fos = new FileOutputStream(myFile);
                            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                            Toast.makeText(MainActivity.this, "文件已写入！", Toast.LENGTH_SHORT).show();
                            osw.write(et.getText().toString());
                            osw.flush();
                            osw.close();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myFile.exists()) {
                    try {
                        FileInputStream fis = new FileInputStream(myFile);
                        InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
                        char[] words = new char[fis.available()];
                        isr.read(words);
                        String text = new String(words);
                        tv.setText(text );
                        Toast.makeText(MainActivity.this, "文件已经读取！", Toast.LENGTH_SHORT).show();
                        isr.close();
                        fis.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



}
