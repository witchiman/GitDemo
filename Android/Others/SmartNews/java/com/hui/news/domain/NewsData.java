package com.hui.news.domain;

import java.util.ArrayList;

/**
 * Created by HUI on 2016/4/10.
 */
public class NewsData {
    public String retcode;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData {
        public String id;
        public String title;
        public String type;
        public ArrayList<NewsTabData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "children=" + children +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public class NewsTabData {
        public String  id;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "data=" + data +
                '}';
    }
}
