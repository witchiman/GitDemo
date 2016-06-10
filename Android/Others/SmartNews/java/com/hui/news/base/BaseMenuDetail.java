package com.hui.news.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by HUI on 2016/4/11.
 */
public abstract class BaseMenuDetail {
    public Activity mActivity;
    public View mRootView;

    public BaseMenuDetail(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {};
}
