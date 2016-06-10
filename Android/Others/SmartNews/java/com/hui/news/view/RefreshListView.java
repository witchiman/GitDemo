package com.hui.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hui.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HUI on 2016/4/19.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private int startY  = -1;
    private int headHeight;
    private View headerView;

    private static final int STATE_PULL_REFRESH = 0;
    private static final int STATE_RELEASE_REFRESH = 1;
    private static final int STATE_REFRESHING = 2;
    private int currentState = STATE_PULL_REFRESH;

    private ImageView mIvArr;
    private ProgressBar mProgressBar;
    private TextView mTvText;
    private TextView mTvDate;
    private RotateAnimation rotateDown;
    private RotateAnimation rotateUp;

    private RefreshListener mRefreshListener;
    private View footerView;
    private int footHeight;

    private boolean isLoading = false; //标记是否加载

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }


    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    /**
     * 增加下拉刷新的HeaderView
     */
    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.refresh_header_view, null);
        this.addHeaderView(headerView);
        mIvArr = (ImageView) headerView.findViewById(R.id.iv_refresh_arr);
        mProgressBar = (ProgressBar) headerView.findViewById(R.id.progressBar);
        mTvText = (TextView) headerView.findViewById(R.id.tv_refresh_text);
        mTvDate = (TextView) headerView.findViewById(R.id.tv_refresh_date);

        headerView.measure(0, 0);
        //获取HeaderView的高度
        headHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0,-headHeight, 0, 0);

        initAnimation();   //初始化动画
        mTvDate.setText("最新更新时间："+ getCurrentTime());
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.refresh_footer_view, null);
        this.addFooterView(footerView);

        footerView.measure(0, 0);
        footHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footHeight, 0, 0);
        this.setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == STATE_REFRESHING) {   //如果正在刷新，不予处理
                    break;
                }

                if (startY ==-1) {                   //确保startX有效
                    startY = (int) ev.getRawY();
                }

                int endY = (int) ev.getRawY();
                int dY = endY - startY;

                if (dY>0 && getFirstVisiblePosition()==0) {// 如果是下拉且为第一个条目显示headerView
                    int padding = dY- headHeight;
                    headerView.setPadding(0, padding, 0, 0);
                    if (padding > 0 && currentState!=STATE_RELEASE_REFRESH) {  //状态设置为松开刷新
                        currentState = STATE_RELEASE_REFRESH;
                        refreshState();
                    } else if (padding < 0 && currentState!=STATE_PULL_REFRESH){  //状态设置为下拉刷新
                        currentState = STATE_PULL_REFRESH;
                        refreshState();
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                startY = -1;  //重新初始化值
                if (currentState == STATE_PULL_REFRESH) {
                    headerView.setPadding(0,-headHeight, 0, 0);
                } else if (currentState == STATE_RELEASE_REFRESH) {
                    currentState = STATE_REFRESHING;
                    refreshState();
                    headerView.setPadding(0, 0, 0, 0);
                }
                break;
            default:
                break;
        }


        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (currentState) {
            case STATE_PULL_REFRESH:
                mTvText.setText("下拉刷新");
                mIvArr.setVisibility(VISIBLE);
                mIvArr.startAnimation(rotateDown);
                mProgressBar.setVisibility(INVISIBLE);
                break;
            case STATE_RELEASE_REFRESH:
                mTvText.setText("松开刷新");
                mIvArr.startAnimation(rotateUp);
                break;
            case STATE_REFRESHING:
                mTvText.setText("正在刷新");
                mIvArr.setVisibility(INVISIBLE);
                mIvArr.clearAnimation();   //必须清理掉动画
                mProgressBar.setVisibility(VISIBLE);
                mRefreshListener.onRefresh();
                break;
            default:
                break;
        }
    }

    //增加旋转动画
    private void initAnimation() {
        rotateUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateUp.setDuration(200);
        rotateUp.setFillAfter(true);
        rotateDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateDown.setDuration(200);
        rotateDown.setFillAfter(true);
    }

    public void setRefreshListener(RefreshListener listener) {
        mRefreshListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_FLING || scrollState==SCROLL_STATE_IDLE) {
                if (getLastVisiblePosition() == getCount()-1) {      //滑动最后才显示footerView
                    //Log.d("RefreshListView","最后一个位置");
                    footerView.setPadding(0, footHeight, 0, 0);
                    setSelection(getCount()-1); //改变listView显示位置

                    isLoading = true;

                    if (mRefreshListener != null) {
                        mRefreshListener.onLoadMore();
                    }
                }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 用以监听下拉刷新的接口
     */
    public interface RefreshListener {
        public void onRefresh();
        public void onLoadMore();
    }

    /**
     * 处理下拉刷新和加载更多页面后的操作
     * @param sucess
     */
    public void onRefreshOrLoad(boolean sucess) {
        if (isLoading) {
            isLoading = false;
            footerView.setPadding(0, -footHeight, 0, 0);
        }else {
            currentState = STATE_PULL_REFRESH;
            mTvText.setText("下拉刷新");
            mIvArr.setVisibility(VISIBLE);
            mProgressBar.setVisibility(INVISIBLE);
            headerView.setPadding(0, -headHeight, 0, 0);
            if (sucess) {
                mTvDate.setText("最新更新时间：" + getCurrentTime());
            }
        }
    }

    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
	
	
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemClickListener.onItemClick(parent, view, position-2, id);
    }

	
    private OnItemClickListener itemClickListener;
    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(this);
        itemClickListener = listener;
    }
}
