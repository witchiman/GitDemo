package com.hui.news.base.menuiml;

import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hui.news.R;
import com.hui.news.base.BaseMenuDetail;
import com.hui.news.domain.PhotosData;
import com.hui.news.global.GlobalConstants;
import com.hui.news.utils.HttpUtil;
import com.hui.news.utils.bitmap.MyBitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by HUI on 2016/4/11.
 */
public class PhotoMenuDetail extends BaseMenuDetail {
    private static final String TAG = "PhotoMenuDetail";
    private GridView gvPhoto;
    private ListView lvPhoto;
    private ArrayList<PhotosData.PhotoInfo> photoList;

    private ImageView btnPhoto;

    public PhotoMenuDetail(Activity activity) {
        super(activity);
    }

    public PhotoMenuDetail(Activity mActivity, ImageView btnPhoto) {
        super(mActivity);
        this.btnPhoto = btnPhoto;
        btnPhoto.setVisibility(View.VISIBLE);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();
            }
        });
    }

    private boolean isList = true;

    /**
     * 切换显示方式
     */
    private void changeDisplay() {
        if (isList) {
            isList = false;
            btnPhoto.setImageResource(R.drawable.icon_pic_list_type);
            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);
        } else {
            isList = true;
            btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer(GlobalConstants.PHOTO_URL);

    }

    private void getDataFromServer(String adress) {
        RequestParams params = new RequestParams(adress);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "下载出错");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        PhotosData photosData = gson.fromJson(result, PhotosData.class);
        photoList = photosData.data.news;
        if (photoList != null) {
            lvPhoto.setAdapter(new PhotoAdapter());
            gvPhoto.setAdapter(new PhotoAdapter());
        }
    }

    class PhotoAdapter extends BaseAdapter {
        //ImageOptions options = null;
        MyBitmapUtils bitmapUtils = null; //使用自定义的BitmapUtils
        public PhotoAdapter() {
           /* this.options = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setLoadingDrawableId(R.drawable.news_pic_default)
                    .setFailureDrawableId(R.drawable.news_pic_default)
                    .build();*/
            bitmapUtils = new MyBitmapUtils();
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public PhotosData.PhotoInfo getItem(int position) {
            return photoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PhotosData.PhotoInfo photoInfo = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView  = View.inflate(mActivity, R.layout.list_photo_item, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_photo);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_photo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
           // x.image().bind(holder.iv, HttpUtil.processUrl(photoInfo.listimage), options);
            bitmapUtils.display(holder.iv,HttpUtil.processUrl(photoInfo.listimage));
            holder.tv.setText(photoInfo.title);

            return convertView;
        }
        class ViewHolder {
            ImageView iv;
            TextView tv;
        }
    }
}
