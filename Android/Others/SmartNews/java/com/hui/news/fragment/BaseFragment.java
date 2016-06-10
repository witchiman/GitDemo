package com.hui.news.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hui.news.R;

import org.xutils.x;

/**
 * 用于填充布局
 * Created by HUI on 2016/4/8.
 */
public abstract class  BaseFragment extends Fragment {
    private boolean injected = false;
    protected Activity mActivity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // injected = true;
       // x.view().inject(this, inflater, container);
        return initViews();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*if (!injected) {
            x.view().inject(this, this.getView());
        }*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public abstract View initViews();

    public  void initData() {

    };


}
