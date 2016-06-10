package com.hui.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 自定义ViewPager，设置父View不拦截滑动事件
 * Created by HUI on 2016/4/17.
 */
public class TabDetailViewPager extends ViewPager {

    private int startX;
    private int startY;

    public TabDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabDetailViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);//一开始设置不拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                if (Math.abs(endX- startX) > Math.abs(endY- startY)) {  //左右滑
                    if (endX- startX > 0) { //向右滑
                        if (getCurrentItem() == 0) {  //如果是向右滑且为第一个页面拦截事件
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {//向左滑
                        if (getCurrentItem() == getAdapter().getCount()-1) { //如果是向左滑且为最后一个页面拦截事件
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                    //Log.d("TabDetailViewPager", String.valueOf(endX-startX));

                } else { //上下滑
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
