package com.hui.news;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.hui.news.fragment.ContentFragment;
import com.hui.news.fragment.LeftmenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.xutils.x;

public class MainActivity extends SlidingFragmentActivity {

    private static final String CONTENT_FRAGMENT = "content_fragment";
    private static final String LEFTMENU_FRAGMENT = "left_menu_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);

        SlidingMenu slidingMenu = getSlidingMenu();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size); //获取屏幕的宽和高
        slidingMenu.setBehindOffset((int) (0.5*size.x));  //设置滑动菜单的预留宽度适配不同屏幕
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        initFragment();
    }

    /**
     * 初始化布局用Fragment填充页面
     */
    private void initFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.view_content, new ContentFragment(),CONTENT_FRAGMENT);
        transaction.replace(R.id.view_leftmenu, new LeftmenuFragment(), LEFTMENU_FRAGMENT);
        transaction.commit();
    }

    public LeftmenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (LeftmenuFragment) fm.findFragmentByTag(LEFTMENU_FRAGMENT);
    }

    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (ContentFragment) fm.findFragmentByTag(CONTENT_FRAGMENT);
    }
}
