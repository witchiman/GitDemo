package com.hui.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager，设置父View不拦截滑动事件（设置监听TabPageIndicator滑动事件设置SlidingMenu后不用）
 * Created by HUI on 2016/4/17.
 */
public class NewsDetailViewPager extends ViewPager {
    public NewsDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsDetailViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem() != 0) {  //第一项不拦截
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
