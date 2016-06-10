package com.hui.listview_chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private Button button;
    private ListView list;
    private EditText et;
    private List<Msg> msgList;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        et = (EditText) findViewById(R.id.edit_send);
        initMsg();
        adapter = new MsgAdapter(this,R.layout.list_chat,msgList);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(this);

        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et.getText().toString();
                if(text.equals("")) {
                    Toast.makeText(MainActivity.this, "消息不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Msg msg = new Msg(text, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    list.setSelection(msgList.size());//listview定位到最后一行s
                    et.setText("");
                }
            }
        });
    }

    public void initMsg() {
        msgList = new ArrayList<Msg>();
        Msg msg1 = new Msg("Hi,this is Tom!",Msg.TYPE_RECIEVE);
        Msg msg2 = new Msg("Oh,I got it!",Msg.TYPE_SEND);
        msgList.add(msg1);
        msgList.add(msg2);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("删除此项");
        dialog.setMessage("您确定要删除吗?");
        dialog.setNegativeButton("Cancel",null);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msgList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.show();
        return true;
    }
}
