package com.hui.mobileguard.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hui.mobileguard.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    private ListView lvContact;
    private ArrayList<HashMap<String, String>> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        lvContact = (ListView) findViewById(R.id.lv_contacts);

        SimpleAdapter adapter = new SimpleAdapter(this, readContact(), R.layout.list_contact_item,
                new String[] {"name", "number"}, new int[] {R.id.tv_name, R.id.tv_number});
        lvContact.setAdapter(adapter);

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactActivity.this, Setup3Activity.class);
                intent.putExtra("phone", contactsList.get(position).get("number"));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public ArrayList<HashMap<String, String>> readContact() {
        contactsList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);
            while(cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<String, String>();
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                map.put("name",name);
                map.put("number",number);
                contactsList.add(map);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return contactsList;
    }
}
