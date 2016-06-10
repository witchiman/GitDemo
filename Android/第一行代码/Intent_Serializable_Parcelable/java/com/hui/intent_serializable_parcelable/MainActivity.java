package com.hui.intent_serializable_parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                Person1 person1 = new Person1();
                person1.setName("Tom");
                person1.setAge(22);
                Person2 person2 = new Person2();
                person2.setName("Jimmy");
                person2.setAge(23);
                intent.putExtra("person1",person1 );
                intent.putExtra("person2",person2);
                startActivity(intent);
            }
        });
    }


}
