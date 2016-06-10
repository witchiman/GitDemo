package com.hui.news.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hui.news.MainActivity;
import com.hui.news.R;
import com.hui.news.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by HUI on 2016/4/8.
 */
public class LeftmenuFragment extends BaseFragment {
    private ListView newsList;
    private ArrayList<NewsData.NewsMenuData> mMenuList;
    private int currentItemPos;
    private NewsAdapter newsAdapter;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        newsList = (ListView) view.findViewById(R.id.list_left_menu);

        return view;
    }

    @Override
    public void initData() {

        /*监听侧边栏点击事件*/
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItemPos = position;
                newsAdapter.notifyDataSetChanged();

                /*设置新闻中心内容页的切换*/
                ((MainActivity)mActivity).getContentFragment().getNewsCenterPager().
                        setCurrentMenuDetail(position);

                toggleSlidingMenu();

            }
        });
    }

    /*设置侧边栏出现或消失*/
    public void toggleSlidingMenu() {
        SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu();
        slidingMenu.toggle();
    }
    public void setMenuData(NewsData data) {
        Log.d("LeftmenuFragment", data + "");
        mMenuList = data.data;
        newsAdapter = new NewsAdapter();
        newsList.setAdapter(newsAdapter);
    }

    private class NewsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_left_menu, null);
            TextView tvMenu = (TextView) view.findViewById(R.id.tv_left_menu);

            NewsData.NewsMenuData newsMenuData = getItem(position);
            tvMenu.setText(newsMenuData.title);
            if (currentItemPos == position) {
                tvMenu.setEnabled(true);
            } else {
                tvMenu.setEnabled(false);
            }

            return view;
        }
    }
}
