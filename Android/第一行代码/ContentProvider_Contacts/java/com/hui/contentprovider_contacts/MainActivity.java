package com.hui.contentprovider_contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> contacts;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);

        readContats();
        listView.setAdapter(adapter);


    }

    public void readContats() {
        Cursor cursor = null;
       try {
           cursor = getContentResolver().query(
                   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);
           while(cursor.moveToNext()) {
               String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
               String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
               contacts.add(name + "\n"+ number);
           }
       }catch (Exception e) {
           e.printStackTrace();
       }finally {
           if(cursor != null) {
               cursor.close();
           }
       }
    }


}
