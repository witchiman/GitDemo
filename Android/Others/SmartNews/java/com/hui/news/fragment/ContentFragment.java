package com.hui.news.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hui.news.R;
import com.hui.news.base.BasePager;
import com.hui.news.base.pageriml.GovAffairsPager;
import com.hui.news.base.pageriml.HomePager;
import com.hui.news.base.pageriml.NewsCenterPager;
import com.hui.news.base.pageriml.SettingPager;
import com.hui.news.base.pageriml.SmartServicePager;
import com.hui.news.view.ViewPagerNoScroll;

import java.util.ArrayList;

/**
 * Created by HUI on 2016/4/8.
 */
public class ContentFragment extends BaseFragment{

    public ViewPagerNoScroll vpContent;
    private ArrayList<BasePager> mPagerList;
    public RadioGroup rgGroup;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content,null);
        vpContent = (ViewPagerNoScroll) view.findViewById(R.id.vp_cotent);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_content);
        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.tab_home);
        mPagerList = new ArrayList<BasePager>();
        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new GovAffairsPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));

        vpContent.setAdapter(new ContentAdapter());

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.tab_newscenter:
                        vpContent.setCurrentItem(1,false);
                        break;
                    case R.id.tab_smartservice:
                        vpContent.setCurrentItem(2, false);
                        break;
                    case R.id.tab_govaffairs:
                        vpContent.setCurrentItem(3, false);
                        break;
                    case R.id.tab_setting:
                        vpContent.setCurrentItem(4, false);
                        break;
                }
            }
        });
        mPagerList.get(0).initData();   //由于最初没有选择事件被监听，需要初始化数据首页数据

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();  //初始化选择页面的数据
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     *添加五个子页
     */
    private class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);

            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
    /**
     * 获取闻闻中心的内容页
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagerList.get(1);
    }
}
