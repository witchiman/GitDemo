package com.hui.news;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hui.news.utils.HttpUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.concurrent.ConcurrentNavigableMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by HUI on 2016/4/21.
 */
public class NewsActivity extends Activity implements View.OnClickListener {

    private ImageView ivBack;
    private ImageView ivShare;
    private ImageView ivTextSize;
    private WebView wvNews;
    @ViewInject(R.id.news_progressbar)
    private ProgressBar progressBar;

    private static final String TAG = "NewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);
        x.view().inject(this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivTextSize = (ImageView) findViewById(R.id.iv_textSize);
        wvNews = (WebView) findViewById(R.id.wv_news);

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivTextSize.setOnClickListener(this);

        WebSettings settings = wvNews.getSettings();
        settings.setJavaScriptEnabled(true);

        wvNews.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);      //设置只在Webview跳转
                return true;
            }
        });

        wvNews.setWebChromeClient(new WebChromeClient() {
            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.d(TAG, "标题：" + title);
                super.onReceivedTitle(view, title);
            }
            //获取网页加载的进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG,"加载：" + newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        String url = HttpUtil.processUrl(getIntent().getStringExtra("url"));
        wvNews.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShare();
                break;
            case R.id.iv_textSize:
                showTextSizeDialog();
                break;
            default:
                break;
        }
    }
    private int currentChooseItem; //记录当前选择的item
    private int choosedItem = 2; //记录之前选择的item

    /**
     * 设置字体对话框
     */
    private void showTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        String[] items = new String[] {"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
        builder.setSingleChoiceItems(items, choosedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentChooseItem = which;
            }
        });
        //设置字体大小
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = wvNews.getSettings();
                switch (currentChooseItem) {
                    case 0:
                        settings.setTextZoom(200);
                        break;
                    case 1:
                        settings.setTextZoom(150);
                        break;
                    case 2:
                        settings.setTextZoom(100);
                        break;
                    case 3:
                        settings.setTextZoom(75);
                        break;
                    case 4:
                        settings.setTextZoom(50);
                        break;
                    default:
                        break;
                }
                choosedItem = currentChooseItem;
            }
        });

        builder.setNegativeButton("取消",null);
        builder.show();
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.ssdk_oks_share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(wvNews.getTitle());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(wvNews.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("测试文本的评论");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

}

