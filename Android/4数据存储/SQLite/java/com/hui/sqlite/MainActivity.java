package com.hui.sqlite;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    private SimpleCursorAdapter adapter;
    private DB db;
    private SQLiteDatabase dbWrite,dbRead;

    private Button button;
    private EditText etName,etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);

        /*写入数据*/
        /*SQLiteDatabase dbWrite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name","Tom");
        cv.put("age",22);
        dbWrite.insert("user", null, cv); //第二个参数为指定的填充值
        cv.clear();
        cv.put("name","小明");
        cv.put("age",13);
        dbWrite.insert("user", null, cv);*/

        /*读取数据*/
       /* SQLiteDatabase dbRead = db.getReadableDatabase();
        Cursor cursor =  dbRead.query("user",null, null,null,null,null,null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            Log.d("MainActivity","name: "+ name + "age: " + age);
        }*/


        /*初始化页面数据*/
        initView();

        /*页面添加*/
        addView();

        /*页面项操作*/
        doView();



    }

    private void reviseView() {

    }

    private void doView() {
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setTitle("提醒").setMessage("确定要删除此项吗？").setNeutralButton("这个按钮没用", null).setNegativeButton("取消", null).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor cursor = adapter.getCursor();
                                cursor.moveToPosition(position);
                                int itemId = cursor.getInt(cursor.getColumnIndex("_id"));
                                dbWrite.delete("user", "_id=?", new String[]{itemId + ""});
                                refreshView();
                            }
                        }).show();
                return true;
            }
        });
    }

    private void addView() {
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbRead.query("user",null,"name=?",new String[]{etName.getText().toString()},null,null,null);
                ContentValues cv = new ContentValues();
                cv.put("name", etName.getText().toString());
                cv.put("age", etAge.getText().toString());

                if(cursor.moveToNext()) {
                    dbRead.update("user",cv,"name=?",new String[]{etName.getText().toString()});
                    Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }else {
                    dbWrite.insert("user", null, cv);
                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                refreshView();
            }
        });
    }

    private void initView() {
        dbWrite = db.getWritableDatabase();
        dbRead = db.getReadableDatabase();
        adapter = new SimpleCursorAdapter(this,R.layout.user_list_cell,null,new String[]{"name","age"},
                new int[]{R.id.tvName,R.id.tvAge}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER); //第二个参数指定资源文件，第三个参数指定adapter,此处暂设为空
        setListAdapter(adapter);
        refreshView();
    }

    private void  refreshView() {
        Cursor cursor = dbRead.query("user",null,null,null,null,null,null);
        adapter.changeCursor(cursor);
    }

}
