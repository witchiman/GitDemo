package com.hui.news.base.pageriml;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.hui.news.MainActivity;
import com.hui.news.base.BaseMenuDetail;
import com.hui.news.base.BasePager;
import com.hui.news.base.menuiml.InteractMenuDetail;
import com.hui.news.base.menuiml.NewsMenuDetail;
import com.hui.news.base.menuiml.PhotoMenuDetail;
import com.hui.news.base.menuiml.TopicMenuDetail;
import com.hui.news.domain.NewsData;
import com.hui.news.global.GlobalConstants;
import com.hui.news.utils.CacheUtils;
import com.hui.news.utils.HttpCallbackListener;
import com.hui.news.utils.HttpUtil;

import java.util.ArrayList;

/**
 * 新闻中心实现
 * Created by HUI on 2016/4/9.
 */
public class NewsCenterPager extends BasePager {
    public ArrayList<BaseMenuDetail> mMenuList;
    private NewsData newsData;
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        setSlidingMenuEnable(true); //设置侧边栏可用

        String cache = CacheUtils.getCache(GlobalConstants.CATEGORIES_URL,null,mActivity);
        if (!TextUtils.isEmpty(cache)) { //如果cache不为空解析cache
            parseData(cache);
        }
        getDataFromServer(GlobalConstants.CATEGORIES_URL);
    }

    /**
     * 从服务器得到到数据
     */
    private void getDataFromServer(String address) {

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                //System.out.println(response);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseData(response);  //涉及到UI操作，必须回到UI线程
                        CacheUtils.setCache(GlobalConstants.CATEGORIES_URL, response,mActivity);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.d("NewsCenterPager","下载数据失败");
                e.printStackTrace();
            }
        });

    }

    /**
     * 解析网络数据
     * @param data
     */
    public void parseData(String data) {
        Gson gson = new Gson();
        newsData = gson.fromJson(data, NewsData.class);
        Log.d("NewsCenterPager", newsData + "");
        ((MainActivity)mActivity).getLeftMenuFragment().setMenuData(newsData);

        /*添加四个详情页*/
        mMenuList = new ArrayList<BaseMenuDetail>();
        mMenuList.add(new NewsMenuDetail(mActivity, newsData.data.get(0).children));
        mMenuList.add(new TopicMenuDetail(mActivity));
        mMenuList.add(new PhotoMenuDetail(mActivity, btnPhoto));
        mMenuList.add(new InteractMenuDetail(mActivity));

        setCurrentMenuDetail(0);
    }

    public void setCurrentMenuDetail(int position) {
        flContent.removeAllViews(); //清楚之前的View，避免叠加
        BaseMenuDetail pager = mMenuList.get(position);
        flContent.addView(pager.mRootView);
        tvTitle.setText(newsData.data.get(position).title);

        if (pager instanceof PhotoMenuDetail) {
            btnPhoto.setVisibility(View.VISIBLE);
        } else {
            btnPhoto.setVisibility(View.GONE);
        }

        pager.initData(); //页签初始化数据
    }

}
