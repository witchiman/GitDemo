package com.example.hui.learncomponents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Checkbox extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView textView;
    private CheckBox cb1, cb2, cb3, cb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);
        textView = (TextView) findViewById(R.id.tvCheckbox);
        cb1 = (CheckBox) findViewById(R.id.c1);
        cb2= (CheckBox) findViewById(R.id.c2);
        cb3 = (CheckBox) findViewById(R.id.c3);
        cb4 = (CheckBox) findViewById(R.id.c4);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkbox, menu);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String str = "你喜欢的食物有";
        if(cb1.isChecked()) {
            str += cb1.getText() + ",";
        }
        if(cb2.isChecked()) {
            str += cb2.getText() + ",";
        }
        if(cb3.isChecked()) {
            str += cb3.getText() + ",";
        }
        if(cb4.isChecked()) {
            str += cb4.getText() + ".";
        }
        textView.setText(str);

    }
}
