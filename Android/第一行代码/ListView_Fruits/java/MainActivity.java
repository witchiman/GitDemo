package com.hui.listview_fruits;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  ListView lv;
    private List<Fruit> fruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);
        initFruits();
        FruitAdapter  adapter = new FruitAdapter(this,R.layout.list_fruit,fruits);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruits.get(position);
                Toast.makeText(MainActivity.this, "您选择的是：" + fruit.getFruitName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initFruits() {
        fruits = new ArrayList<Fruit>();
        Fruit apple  = new Fruit("Apple",R.drawable.img_apple);
        Fruit orange = new Fruit("Orange",R.drawable.img_orange);
        Fruit grape = new Fruit("Grape",R.drawable.img_grape);
        fruits.add(apple);
        fruits.add(orange);
        fruits.add(grape);
    }

}
