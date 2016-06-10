package com.hui.slidingmenudemo;

import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.left_menu);//绑定左侧边栏

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置全屏触摸

        slidingMenu.setSecondaryMenu(R.layout.right_menu); //设置右侧边栏
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT); //设置左右侧边栏都显示

        slidingMenu.setBehindOffset(300);  //设置主屏预留宽度


    }
}
