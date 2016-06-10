package com.hui.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hui.news.utils.DensityUtils;
import com.hui.news.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {
    private static int[] mImagIds = new int[] {R.drawable.guide_1, R.drawable.guide_2,
        R.drawable.guide_3};
    private List<ImageView> imgList;
    private ViewPager vp;
    private LinearLayout pointsGroup;

    private int mPointDistance;
    private View mRedPoint;

    private Button mButtonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        pointsGroup = (LinearLayout) findViewById(R.id.points_group);
        mRedPoint = findViewById(R.id.view_red_point);
        mButtonStart = (Button) findViewById(R.id.btn_start);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setBoolean(GuideActivity.this, "is_first",false);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        initView();
        vp = (ViewPager) findViewById(R.id.vp_guide);
        vp.setAdapter(new GuideAdapter());
        vp.addOnPageChangeListener(new GuidePageListener());
    }

    private void initView() {
        imgList = new ArrayList<ImageView>();
        for (int i=0; i<mImagIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(mImagIds[i]);
            imgList.add(imageView);
        }

        /*加入引导页的3个小圆点*/
        for (int i=0; i<mImagIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtils.dpToPx(this, 10)
                ,DensityUtils.dpToPx(this, 10));
            if (i>0) {
                layoutParams.leftMargin = 30;
            }
            point.setLayoutParams(layoutParams);
            pointsGroup.addView(point);
        }

        pointsGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /*获取两个灰色小圆点之间的距离*/
                mPointDistance = pointsGroup.getChildAt(1).getLeft() - pointsGroup.getChildAt(0).getLeft();
            }
        });
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgList.get(position));
            return imgList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int len = (int) (mPointDistance*positionOffset + position*mPointDistance);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
           // System.out.println("leftmargin is :" + layoutParams.leftMargin);
            layoutParams.leftMargin = len; //设置左边距
            mRedPoint.setLayoutParams(layoutParams);

        }

        @Override
        public void onPageSelected(int position) {
            /*设置引导页按钮在最后页卡出现*/
            if (position == mImagIds.length-1) {
                mButtonStart.setVisibility(View.VISIBLE);
            }else {
                mButtonStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
