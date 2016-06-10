package com.hui.news.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.hui.news.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by HUI on 2016/4/24.
 */
public class LocalCacheUtils {
    private static final String TAG = "LocalCacheUtils";
    private static final String LOCAL_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/YiXingNews";

    public Bitmap getBitmapFromLocal(String url) {
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(LOCAL_PATH, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                Log.d(TAG, "getBitmapFromLocal:从本地获取图片");
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setBitmapToLocal(String url,Bitmap bitmap) {
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(LOCAL_PATH, fileName);
            File parent  = file.getParentFile();
            if (!parent.exists()) {   //如果文件夹不存在创建
                parent.mkdirs();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));//保存图片
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
