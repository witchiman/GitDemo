package com.hui.news.domain;

import java.util.ArrayList;

/**
 * Created by HUI on 2016/4/23.
 */
public class PhotosData {
    public int retcode;
    public PhotosInfo data;

    public  class PhotosInfo {
        public ArrayList<PhotoInfo> news;
        public String title;
    }

    public class PhotoInfo {
        public String id;
        public String listimage;
        public String title;
        public String type;
    }
}
