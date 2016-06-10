package com.hui.custommenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by Hui on 2015/12/6.
 */
public class MainUI extends RelativeLayout {
    private FrameLayout middleMenu;
    private  FrameLayout leftMenu;
    private  FrameLayout rightMenu;
    private  Context context;
    private Scroller mScroller;

    private FrameLayout middleMask;

    public static final int LEFT_ID = 6782;
    public static final int RIGHT_ID = 6783;
    public static final int MIDDLE_ID = 6784;

    public MainUI(Context context) {
        super(context);
        init(context);
    }

    public MainUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        mScroller = new Scroller(context,new  DecelerateInterpolator());
        middleMenu = new FrameLayout(context);
        middleMask = new FrameLayout(context);
        leftMenu = new FrameLayout(context);
        rightMenu = new FrameLayout(context);
        middleMenu.setBackgroundColor(Color.GREEN);
        middleMask.setBackgroundColor(0x88000000);
        leftMenu.setBackgroundColor(Color.RED);
        rightMenu.setBackgroundColor(Color.BLUE);
        middleMenu.setId(MIDDLE_ID);
        leftMenu.setId(LEFT_ID);
        rightMenu.setId(RIGHT_ID);

        addView(leftMenu);
        addView(middleMenu);
        addView(rightMenu);
        addView(middleMask);

        middleMask.setAlpha(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        middleMenu.measure(widthMeasureSpec, heightMeasureSpec);
        middleMask.measure(widthMeasureSpec, heightMeasureSpec);
        int realWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tempWidth = MeasureSpec.makeMeasureSpec(
                (int)(realWidth*0.8f), MeasureSpec.EXACTLY);
        leftMenu.measure(tempWidth, heightMeasureSpec);
        rightMenu.measure(tempWidth, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        middleMenu.layout(l, t, r, b);
        middleMask.layout(l, t, r, b);
        leftMenu.layout(l - leftMenu.getMeasuredWidth(), t, r, b);
        rightMenu.layout(l+ middleMenu.getMeasuredWidth(), t,
                l+ middleMenu.getMeasuredWidth()+ rightMenu.getMeasuredWidth(), b);
    }

    //重写scrollTo()实现蒙版渐变
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        int curX = Math.abs(getScrollX());
        float alpha = curX/(float)leftMenu.getMeasuredWidth();  //值从小到大
        middleMask.setAlpha(alpha);
    }

    private boolean isTestComplete = false;
    private boolean isLeftOrRight = false;
    private Point point = new Point();
    public static final int TEST_DIS = 20;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isTestComplete) {
            getEventType(ev);
            return true;
        }
        if(isLeftOrRight) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_MOVE:
                    int curScrollX = getScrollX();
                    int dis_x = (int) ( ev.getX() - point.x);
                    int  expectX  = curScrollX - dis_x;
                    int finalX = 0;
                    if(expectX < 0) {
                        finalX = Math.max(expectX, -leftMenu.getMeasuredWidth());
                        System.out.println("这是向右滑？");
                    }else {
                        finalX = Math.min(expectX, rightMenu.getMeasuredWidth());
                        System.out.println("向左滑");
                    }
                    scrollTo(finalX, 0);
                    point.x = (int) ev.getX();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //添加动画效果
                    curScrollX = getScrollX();
                    if(Math.abs(curScrollX) > (leftMenu.getMeasuredWidth()/2)) {
                        if(curScrollX < 0) {
                            //startScroll（）方法最后一个参数指定动画的持续时间，可加可不加
                            mScroller.startScroll(curScrollX, 0, -leftMenu.getMeasuredWidth()-curScrollX, 0);
                        }else {
                            mScroller.startScroll(curScrollX, 0, rightMenu.getMeasuredWidth()-curScrollX, 0);
                        }
                    }else {
                        mScroller.startScroll(curScrollX, 0, -curScrollX, 0);
                    }
                    invalidate();
                    isLeftOrRight = false;
                    isTestComplete = false;
                    break;

            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(!mScroller.computeScrollOffset()) {
            return;
        }
        int tempX = mScroller.getCurrX();
        scrollTo(tempX, 0);
    }

    private void getEventType(MotionEvent ev) {
        switch(ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                point.x = (int) ev.getX();
                point.y = (int) ev.getY();
                super.dispatchTouchEvent(ev); //将事件分发给系统
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = (int) Math.abs(ev.getX() - point.x);
                int dY = (int) Math.abs(ev.getY() - point.y);
                if(dX >= TEST_DIS && dX>dY ) { //左右滑动
                    isLeftOrRight = true;
                    isTestComplete = true;
                }else if(dY> TEST_DIS && dY>dX) {
                    isLeftOrRight = false;
                    isTestComplete = true;
                }
                point.x = (int) ev.getX();
                point.y = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                super.dispatchTouchEvent(ev);  //将事件分发给系统
                isLeftOrRight = false;
                isTestComplete = false;
                break;

        }
    }
}
