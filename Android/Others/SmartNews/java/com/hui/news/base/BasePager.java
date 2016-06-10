package com.hui.news.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.news.MainActivity;
import com.hui.news.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by HUI on 2016/4/9.
 */
public abstract class BasePager {
    public Activity mActivity;
    public View mRootView;
    public  TextView tvTitle;
    public FrameLayout flContent;
    public ImageButton ivMenu;
    public  ImageView btnPhoto;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
        ivMenu = (ImageButton) mRootView.findViewById(R.id.iv_menu);

        btnPhoto = (ImageView) mRootView.findViewById(R.id.iv_photo_change);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }

    /*设置侧边栏出现或消失*/
    public void toggleSlidingMenu() {
        SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu();
        slidingMenu.toggle();
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置侧边栏菜单是否可用
     * @param enable
     */
    public void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
