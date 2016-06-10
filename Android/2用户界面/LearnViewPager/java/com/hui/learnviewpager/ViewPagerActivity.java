package com.hui.learnviewpager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private List<View> views;
    private ViewPager vp;
    private  ViewPagerAdapter vpAdapter;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3};
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        views = new ArrayList<View>();
        views.add(getLayoutInflater().inflate(R.layout.one, null));
        views.add(getLayoutInflater().inflate(R.layout.two, null));
        views.add(getLayoutInflater().inflate(R.layout.three, null));

        vp = (android.support.v4.view.ViewPager) findViewById(R.id.viewpaper);
        vpAdapter = new ViewPagerAdapter(views, this);

        vp.setAdapter(vpAdapter);
        initDots();
        vp.addOnPageChangeListener(this);

        btn = (Button) views.get(2).findViewById(R.id.btnJump);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void initDots() {
        dots = new ImageView[views.size()];
        for(int i=0;i <views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i< views.size(); i++) {
            if(position == i) {
                dots[i].setImageResource(R.drawable.selected);
            }else {
                dots[i].setImageResource(R.drawable.unselected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
