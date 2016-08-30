package com.hui.databingding_recycleview;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hui.databingding_recyclerview.databinding.ActivityMainBinding;
import com.hui.databingding_recyclerview.databinding.ItemPeopleBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<People> peoples;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        peoples = new ArrayList<People>();


        for (int i=1; i<=10; i++) {
            People people = new People();
            people.name.set("Jim" + i);
            Random random = new Random();
            people.gender.set(random.nextBoolean() ? "male":"female" );
            people.age.set(20 + random.nextInt(10));
            peoples.add(people);
        }

        adapter = new MyAdapter(peoples);
        adapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void OnItemClick(View view, People people) {
                Toast.makeText(MainActivity.this, "你点击的是：" + people.name.get(), Toast.LENGTH_SHORT).show();
            }
        });




        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,
                false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

       // delayChange();
    }

    private void delayChange() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=1; i<=10; i++) {
                    peoples.get(i-1).name.set("Tom" + i);
                    Random random = new Random();
                    peoples.get(i-1).gender.set(random.nextBoolean() ? "male":"female" );
                    peoples.get(i-1).age.set(20 + random.nextInt(10));
                }
                Toast.makeText(MainActivity.this, "Hi!"+peoples.size(), Toast.LENGTH_SHORT).show();
            }
        }, 2000);

    }


}
