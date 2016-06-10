package com.hui.fragment_news;

import android.widget.ArrayAdapter;

import java.lang.reflect.Array;

/**
 * Created by HUI on 2016/1/16.
 */
public class News {
    private String title;
    private String content;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
