package com.hui.intent_serializable_parcelable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Person1 person1 = (Person1) getIntent().getSerializableExtra("person1");
        Person2 person2 = getIntent().getParcelableExtra("person2");

        tv = (TextView) findViewById(R.id.personinfo);

        tv.append(person1.getName() + " "+ person1.getAge()+"\n");
        tv.append(person2.getName() + " " + person2.getAge() );
    }
}
