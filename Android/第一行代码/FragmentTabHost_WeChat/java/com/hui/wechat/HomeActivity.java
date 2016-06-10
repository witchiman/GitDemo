package com.hui.wechat.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.hui.wechat.base.BaseActivity;
import com.hui.wechat.fragment.MyFragment;
import com.hui.wechat.R;
import com.hui.wechat.widget.TabIndicatorView;

public class HomeActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost tabHost;
    private static final String TAG_CHAT = "chat";
    private static final String TAG_CONTACT = "contact";
    private static final String TAG_DISCOVER = "discover";
    private static final String TAG_ME = "me";

    private TabIndicatorView chatIndicator;
    private TabIndicatorView contactIndicator;
    private TabIndicatorView discoverIndicator;
    private TabIndicatorView meIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        /*1.初始化FragTabHost*/
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        /*2.新建TabSpec*/
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(TAG_CHAT);
        chatIndicator = new TabIndicatorView(this);
        chatIndicator.setTabTitle("消息");
        chatIndicator.setTabUnRead(12);
        chatIndicator.setTabIcon(R.drawable.tab_icon_chat_normal, R.drawable.tab_icon_chat_focus);
        chatIndicator.setTabSelected(true);   //默认选中第一个
        tabSpec.setIndicator(chatIndicator);

        /*3.添加TabSpec*/
        tabHost.addTab(tabSpec, MyFragment.class, null);

        /*添加第二个,步骤同前*/
        tabSpec = tabHost.newTabSpec(TAG_CONTACT);
        contactIndicator = new TabIndicatorView(this);
        contactIndicator.setTabTitle("通讯录");
        contactIndicator.setTabIcon(R.drawable.tab_icon_contact_normal, R.drawable.tab_icon_contact_focus);
        tabSpec.setIndicator(contactIndicator);
        tabHost.addTab(tabSpec, MyFragment.class, null);

        /*添加第三个*/
        tabSpec = tabHost.newTabSpec(TAG_DISCOVER);
        discoverIndicator = new TabIndicatorView(this);
        discoverIndicator.setTabTitle("发现");
        discoverIndicator.setTabIcon(R.drawable.tab_icon_discover_normal, R.drawable.tab_icon_discover_focus);
        tabSpec.setIndicator(discoverIndicator);
        tabHost.addTab(tabSpec, MyFragment.class, null);

        /*添加第四个*/
        tabSpec = tabHost.newTabSpec(TAG_ME);
        meIndicator = new TabIndicatorView(this);
        meIndicator.setTabTitle("我");
        meIndicator.setTabIcon(R.drawable.tab_icon_me_normal, R.drawable.tab_icon_me_focus);
        tabSpec.setIndicator(meIndicator);
        tabHost.addTab(tabSpec, MyFragment.class, null);

        /*去掉分割线*/
        tabHost.getTabWidget().setDividerDrawable(android.R.color.white);

        /*监听tabHost的选中事件*/
        tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onTabChanged(String tabId) {
        chatIndicator.setTabSelected(false);
        contactIndicator.setTabSelected(false);
        discoverIndicator.setTabSelected(false);
        meIndicator.setTabSelected(false);

        if (tabId.equals(TAG_CHAT)) {
            chatIndicator.setTabSelected(true);
        }else if (tabId.equals(TAG_CONTACT)){
            contactIndicator.setTabSelected(true);
        }else if (tabId.equals(TAG_DISCOVER)) {
            discoverIndicator.setTabSelected(true);
        }else if (tabId.equals(TAG_ME)) {
            meIndicator.setTabSelected(true);
        }
    }
}
