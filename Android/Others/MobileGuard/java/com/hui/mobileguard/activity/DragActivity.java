package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hui.mobileguard.R;

public class DragActivity extends Activity {
    private static final String TAG = "DragActivity";
    private TextView tvTop, tvBottom;
    private ImageView ivDrag;
    private SharedPreferences config;
    private int startY;
    private int startX;
    private int winWidth;
    private int winHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        tvTop = (TextView) findViewById(R.id.tv_top);
        tvBottom = (TextView) findViewById(R.id.tv_bottom);
        ivDrag = (ImageView) findViewById(R.id.iv_drag);
        config = getSharedPreferences("CONFIG", MODE_PRIVATE);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        winWidth = size.x;
        winHeight = size.y;

        /*从本地获取已经设置好的位置信息*/
        int lastX = config.getInt("lastX", 0);
        int lastY = config.getInt("lastY", 0);
       // Log.d(TAG, "onCreate: left is " + lastX);
        //Log.d(TAG, "onCreate: top is " + lastY);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivDrag
                .getLayoutParams();
        layoutParams.leftMargin = lastX;
        layoutParams.topMargin = lastY;

        ivDrag.setLayoutParams(layoutParams);

        ivDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawY();
                        int dX = endX - startX;
                        int dY = endY - startY;
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivDrag
                                .getLayoutParams();
                        int leftMargin = layoutParams.leftMargin;
                        int topMargin = layoutParams.topMargin;
                        leftMargin += dX;
                        topMargin += dY;
                        //Log.d(TAG, "onTouch: " + topMargin +"|"+leftMargin);
                        //防止拖拽到边缘缩放
                        if (leftMargin<0||leftMargin> winWidth-ivDrag.getWidth() ||
                                topMargin<0||topMargin>winHeight-ivDrag.getHeight()-75) {
                            break;
                        }

                        //根据拖拽位置隐藏上下的说明
                        if (topMargin>winHeight/2) {
                            tvBottom.setVisibility(View.INVISIBLE);
                            tvTop.setVisibility(View.VISIBLE);
                        } else {
                            tvBottom.setVisibility(View.VISIBLE);
                            tvTop.setVisibility(View.INVISIBLE);
                        }
                        layoutParams.leftMargin = leftMargin;
                        layoutParams.topMargin = topMargin;
                        ivDrag.setLayoutParams(layoutParams);
                        /*int l = ivDrag.getLeft() + dX;  //另一种方法
                        int r = ivDrag.getRight() + dX;
                        int t = ivDrag.getTop() + dY;
                        int b = ivDrag.getBottom() + dY;
                        ivDrag.layout(l, t, r, b);*/
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        SharedPreferences.Editor editor = config.edit();
                        editor.putInt("lastX", ivDrag.getLeft());
                        editor.putInt("lastY", ivDrag.getTop());
                        editor.commit();
                        break;
                    default:
                        break;
                }

                return false;  //返回false后onClick事件才能得到响应
            }
        });
        ivDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoubleHit();   //双击
            }
        });
    }

    long[] mHits = new long[2];

    /**
     * 多击事件的实现
     */
    private void onDoubleHit( ) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
        mHits[mHits.length-1] = SystemClock.uptimeMillis();
        if (mHits[0] >= SystemClock.uptimeMillis()-500) {
            //Log.d(TAG, "onDoubleHit: 双击啦！");
            ivDrag.layout((winWidth-ivDrag.getWidth())/2,(winHeight-ivDrag.getHeight())/2,
                    (winWidth+ivDrag.getWidth())/2, (winHeight+ivDrag.getHeight())/2);
            config.edit().putInt("lastX", ivDrag.getLeft()).putInt("lastY", ivDrag.getTop())
                    .commit();
        }
    }

}
