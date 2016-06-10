package com.hui.fragment_news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HUI on 2016/1/16.
 */
public class NewsContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_activity);
        /*获取新闻内容*/
        String title = getIntent().getStringExtra("news_title");
        String content = getIntent().getStringExtra("news_content");

        NewsContentFrag newsContentFrag = (NewsContentFrag) getFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFrag.refresh(title, content);
    }

    public static void actionStart(Context context, String title, String content) {
        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",title);
        intent.putExtra("news_content",content);
        context.startActivity(intent);
    }
}
