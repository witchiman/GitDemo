package com.hui.mobileguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.mobileguard.R;
import com.hui.mobileguard.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.transform.Result;

public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";
    private TextView tvFlash;
    private int mVersionCode;
    private String mVersionName;
    private String mDescription;
    private String mDownloadUrl;

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_START_HOME = 4;

    private Handler handler = new Handler(
    ) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SplashActivity.this, "链接错误", Toast.LENGTH_SHORT).show();
                    startHome();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    startHome();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SplashActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                    startHome();
                    break;
                case CODE_START_HOME:
                    startHome();
                    break;
            }
        }
    };

    private TextView tvProgress;
    private RelativeLayout rlRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvFlash = (TextView) findViewById(R.id.tv_flash);
        tvFlash.setText("版本：" + getVersionName());
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        SharedPreferences config = getSharedPreferences("CONFIG", Context.MODE_PRIVATE);
        boolean isAutoUpdate = config.getBoolean("auto_update",true);

        initDB("address.db");   //初始化数据库
        initDB("antivirus.db");

        if (isAutoUpdate) {
            checkVersion();
        }else {
            handler.sendEmptyMessageDelayed(CODE_START_HOME, 2000);   //延迟2s发送消息
        }

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1f);
        animation.setDuration(2000);
        rlRoot.startAnimation(animation);

    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("有新版本:"+ mVersionName);
        builder.setMessage(mDescription);
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startHome();
            }
        });
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadUpdate();
                Log.d(TAG, "onClick: 开始更新");
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {  //设置返回键的监听事件
            @Override
            public void onCancel(DialogInterface dialog) {
                startHome();
            }
        });
        builder.show();
    }
    private String getVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0); //获取包信息
            String versionName = packageInfo.versionName;
            Log.d(TAG, "getVersionName: " + versionName);
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getVersionCode() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0); //获取包信息
            int versionCode = packageInfo.versionCode;
            Log.d(TAG, "getVersionCode: " + versionCode );
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                Message message = Message.obtain();
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://192.168.191.1:8080/update.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        String result = StreamUtils.getStringFromStream(connection.getInputStream());
                        JSONObject object = new JSONObject(result);
                        mVersionCode = object.getInt("versionCode");
                        mVersionName = object.getString("versionName");
                        mDescription = object.getString("description");
                        mDownloadUrl = object.getString("downloadUrl");
                        Log.d(TAG, "new versoncode: " + mVersionCode);
                        if (mVersionCode > getVersionCode()) {
                            message.what = CODE_UPDATE_DIALOG;
                        } else {
                            message.what = CODE_START_HOME;
                        }
                    }

                } catch (MalformedURLException e) {
                    message.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    message.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    message.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    if (duration < 2000) {
                        try {
                            Thread.sleep(2000-duration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(message);
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

        }.start();
    }

    private void startHome() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void downloadUpdate() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) { //判断SD卡是否挂载
            tvProgress.setVisibility(View.VISIBLE);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.hui.mobileguard";//创建用户目录
            File file = new File(path);
            if (!file.exists()) {
                 file.mkdirs();
            }
            String filePath = file.getAbsolutePath()+"/mobileguard.apk";
            Log.d(TAG, "filepath"+ filePath);
            RequestParams params =  new RequestParams(mDownloadUrl);  //设置参数
            Log.d(TAG, "download : " + mDownloadUrl);
            params.setConnectTimeout(5000);
            params.setAutoResume(true);
            params.setSaveFilePath(filePath);
            x.http().get(params, new Callback.ProgressCallback<File>() {
                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    Log.d(TAG, "onLoading: " + current);
                    tvProgress.setText("进度：" + current*100/total + "%" );
                }

                @Override
                public void onSuccess(File result) {
                    Log.d(TAG, "onSuccess: 下载成功");
                    /*打开系统下载安装APK文件*/
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setDataAndType(Uri.fromFile(result),"application/vnd.android.package-archive");
                    startActivityForResult(i, 0);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    Toast.makeText(SplashActivity.this, "下载出错", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode) {
             case 0:
                 startHome();
                 break;
             default:
                 break;
         }
    }

    private void initDB(String dbName) {
        File destPath = new File(getFilesDir(), dbName);
        Log.d(TAG, "initDB: " + getFilesDir());
        if (destPath.exists()) {
            Log.d(TAG, "initDB: the db "+ dbName+ " has been created");
            return;
        }
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in =  getAssets().open(dbName);
            out = new FileOutputStream(destPath);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len=in.read(buffer)) !=-1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
