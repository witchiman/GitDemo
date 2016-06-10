package com.hui.news.base.menuiml;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hui.news.MainActivity;
import com.hui.news.R;
import com.hui.news.base.BaseMenuDetail;
import com.hui.news.base.TabDetailPager;
import com.hui.news.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**新闻页签
 * Created by HUI on 2016/4/11.
 */
public class NewsMenuDetail extends BaseMenuDetail implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    ArrayList<NewsData.NewsTabData> children; //页签详情数据
    ArrayList<TabDetailPager> mPagerList;    //页签ViewPager填充的对象
    private TabPageIndicator indicator;
    private ImageButton mBtnNext;

    public NewsMenuDetail(Activity activity) {
        super(activity);
    }

    public NewsMenuDetail(Activity mActivity, ArrayList<NewsData.NewsTabData> children) {
        super(mActivity);
        this.children = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.menu_news_pager,null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_tab);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        indicator.setOnPageChangeListener(this);
        mBtnNext = (ImageButton) view.findViewById(R.id.btn_next);
        return view;
    }

    @Override
    public void initData() {
         mPagerList = new ArrayList<TabDetailPager>();

        for (int i=0;i<children.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, children.get(i));
            mPagerList.add(pager);
        }
        mViewPager.setAdapter(new MenuDetailAdapter());

        indicator.setViewPager(mViewPager);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(++currentItem);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SlidingMenu slidingMenu = ((MainActivity)mActivity).getSlidingMenu();
        if (mViewPager.getCurrentItem() !=0 ) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }else {
            slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_FULLSCREEN);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MenuDetailAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).title;                          //设置ViewPagerIndicator的标题
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
             container.removeView((View) object);
        }
    }
}
