package com.hui.news.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hui.news.NewsActivity;
import com.hui.news.R;
import com.hui.news.domain.NewsData;
import com.hui.news.domain.TabData;
import com.hui.news.global.GlobalConstants;
import com.hui.news.utils.CacheUtils;
import com.hui.news.utils.HttpCallbackListener;
import com.hui.news.utils.HttpUtil;
import com.hui.news.utils.PrefUtils;
import com.hui.news.view.RefreshListView;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 页签详情页实现
 * Created by HUI on 2016/4/12.
 */
public class TabDetailPager extends BaseMenuDetail implements ViewPager.OnPageChangeListener{
    private static final String TAG = "TabDetailPager" ;
    private ViewPager mTdViewPager;
    private String mUrl = GlobalConstants.SERVER_URL;
    private TabData tabData;
    private ImageOptions imageOptions;
    private ArrayList<TabData.TopNewsData> topNews;
    private CirclePageIndicator mIndicator;

    private TextView mTopNewsTitle;
    private RefreshListView lvNews;
    private List<TabData.TabNewsData>  mNewsList;
    private String moreUrl;
    private NewsAdapter newsAdapter;

    private Handler handler;

    public TabDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mUrl = mUrl + newsTabData.url;
        imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
        View view  = View.inflate(mActivity, R.layout.news_detail, null);
        View headerView = View.inflate(mActivity, R.layout.list_header_news, null);
        mTdViewPager = (ViewPager) headerView.findViewById(R.id.vp_tab_detail);
        mTopNewsTitle = (TextView) headerView.findViewById(R.id.tv_topnews_title);
        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
        lvNews = (RefreshListView) view.findViewById(R.id.lv_tab_detail);


        lvNews.addHeaderView(headerView);  //把头条新闻作为HeaderView加入List里

        lvNews.setRefreshListener(new RefreshListView.RefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer(mUrl);
            }

            @Override
            public void onLoadMore() {
                if (moreUrl!=null) {
                    getMoreDataFromServer(mUrl);
                } else {
                    Toast.makeText(mActivity, "已经到最后一页了", Toast.LENGTH_SHORT).show();
                    lvNews.onRefreshOrLoad(false);   //收起加载更多的布局
                }
            }
        });

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.d("TabDetailPager","Position " + position);

                String ids = PrefUtils.getString(mActivity, "read_ids","");
                if (!ids.contains(mNewsList.get(position).id)) {
                    ids = ids + mNewsList.get(position).id;
                }
                PrefUtils.setString(mActivity,"read_ids",ids);
                readStateChange(view);

                Intent i = new Intent(mActivity, NewsActivity.class);//跳转到新闻页
                i.putExtra("url",mNewsList.get(position).url);
                mActivity.startActivity(i);
            }
        });
        return  view;
    }

    private void  readStateChange(View view) {
        TextView textView = (TextView) view.findViewById(R.id.tv_news);
        textView.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
       // mTdViewPager.addOnPageChangeListener(this);
        String cache = CacheUtils.getCache(mUrl,null,mActivity);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache,false);
        }
        getDataFromServer(mUrl);
    }

    private void getDataFromServer(String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseData(response, false);
                        lvNews.onRefreshOrLoad(true);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvNews.onRefreshOrLoad(false);
                        Toast.makeText(mActivity, "下载数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getMoreDataFromServer(String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseData(response, true);
                        lvNews.onRefreshOrLoad(true);
                        CacheUtils.setCache(mUrl, response, mActivity);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvNews.onRefreshOrLoad(false);
                        Toast.makeText(mActivity, "下载数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void parseData(String response,boolean isMore) {
        Gson gson = new Gson();
        tabData = gson.fromJson(response,TabData.class);
        Log.d("TabDetailPager", tabData.toString());

        //获取更多新闻的链接
        String more = tabData.data.more;
        if (!TextUtils.isEmpty(more)) {
            moreUrl = GlobalConstants.SERVER_URL + more;
        } else {
            moreUrl = null;
        }

        if (!isMore) {
            topNews = tabData.data.topnews;
            mTopNewsTitle.setText(topNews.get(0).title);//初始化头条新闻标题

            mNewsList = tabData.data.news;
            newsAdapter = new NewsAdapter();
            lvNews.setAdapter(newsAdapter);  //初始化新闻列表

            mTdViewPager.setAdapter(new TopNewsAdapter());

            mIndicator.setViewPager(mTdViewPager);
            mIndicator.setSnap(true);  //设置快照显示
            mIndicator.onPageSelected(0); //让指示器重新定位到第一个点
            mIndicator.setOnPageChangeListener(this);

            if (handler == null) {  //首次加载时初始化handler
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                       // Log.d(TAG, "开始轮播了");

                        int currentItem = mTdViewPager.getCurrentItem();
                        if (currentItem < topNews.size() -1) {
                            currentItem++;
                        } else {
                            currentItem = 0;
                        }
                        mTdViewPager.setCurrentItem(currentItem);  //切换到下个页面

                        handler.sendEmptyMessageDelayed(0,3000);     //延迟3s发送消息，形成循环
                        return true;
                    }
                });
            }

            handler.sendEmptyMessageDelayed(0,3000);

        }else {
            List<TabData.TabNewsData> news = tabData.data.news;
            mNewsList.addAll(news);
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabData.TopNewsData newsData = topNews.get(position);
        mTopNewsTitle.setText(newsData.title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TopNewsAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mActivity);

            iv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.d(TAG,"Donw");
                            handler.removeCallbacksAndMessages(null);  //取消轮播
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d(TAG, "UP");
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            TabData.TopNewsData topNew = topNews.get(position);
            x.image().bind(iv, HttpUtil.processUrl(topNew.topimage) ,imageOptions);//设置下载图片
            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class NewsAdapter extends BaseAdapter {
        private ImageOptions options;

        public NewsAdapter() {
            this.options = new ImageOptions.Builder()
                    .setFailureDrawableId(R.drawable.pic_item_list_default)
                    .setLoadingDrawableId(R.drawable.pic_item_list_default)
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .build();
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            TabData.TabNewsData newsItem = (TabData.TabNewsData) getItem(position);

            if (convertView == null) {
                convertView = View.inflate(mActivity,R.layout.list_news_item, null);
                holder = new ViewHolder();
                holder.ivNews = (ImageView) convertView.findViewById(R.id.iv_news_pic);
                holder.tvNews = (TextView) convertView.findViewById(R.id.tv_news);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            x.image().bind(holder.ivNews, HttpUtil.processUrl(newsItem.listimage), options);
            holder.tvNews.setText(newsItem.title);
            holder.tvDate.setText(newsItem.pubdate);

            return convertView;
        }

        class ViewHolder {
            ImageView ivNews;
            TextView tvNews;
            TextView tvDate;
        }
    }
}
