package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hui.mobileguard.R;
import com.hui.mobileguard.dao.AntivirusDao;
import com.hui.mobileguard.domain.ScanInfo;
import com.hui.mobileguard.utils.MD5Utils;

import java.util.List;

public class AntivirusActivity extends Activity {

    private static final int SCANNING = 0;
    private static final int FINISH = 1;
    private static final String TAG = "AntivirusActivity";
    private ProgressBar pbScanner;
    private TextView tvDesc;
    private ImageView ivScanner;
    private LinearLayout llScannedApp;
    private ScrollView svScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antivirus);

        tvDesc = (TextView) findViewById(R.id.tv_init_virus);
        pbScanner = (ProgressBar) findViewById(R.id.pb_scanner);
        ivScanner = (ImageView) findViewById(R.id.iv_scanner);
        llScannedApp = (LinearLayout) findViewById(R.id.ll_scanned_apps);
        svScanner = (ScrollView) findViewById(R.id.sv_scanner);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(Animation.INFINITE);    //设置一直重复
        rotate.setDuration(5000);
        ivScanner.startAnimation(rotate);

        initData();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCANNING:

                    tvDesc.setText("正在扫描病毒...");

                    TextView textView = new TextView(AntivirusActivity.this);
                    ScanInfo scanInfo = (ScanInfo) msg.obj;
                    if (scanInfo.isVirus()) {
                        textView.setTextColor(Color.RED);
                        textView.setText(scanInfo.getAppName() + "有病毒");
                    } else {
                        textView.setTextColor(Color.GRAY);
                        textView.setText(scanInfo.getAppName() + "扫描安全");
                    }

                    llScannedApp.addView(textView);

                    svScanner.post(new Runnable() {   //  自动滚动至底端
                        @Override
                        public void run() {
                                int off =  llScannedApp.getMeasuredHeight() - svScanner.getHeight();
                                if (off > 0) {
                                    svScanner.scrollTo(0, off);
                                }
                        }
                    });

                    break;
                case FINISH:
                    tvDesc.setText("查杀完成");
                    ivScanner.clearAnimation();
                    break;
                default:
                    break;
            }
        }
    };

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AntivirusDao antivirusDao = new AntivirusDao();
                int progress = 0;
                Message message;

                PackageManager packageManager = getPackageManager();
                List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

                pbScanner.setMax(installedPackages.size());

                for (PackageInfo info : installedPackages) {
                    ScanInfo scanInfo = new ScanInfo();
                    scanInfo.setAppName(info.applicationInfo.loadLabel(getPackageManager()).toString());
                    scanInfo.setPackageName(info.packageName);

                    //Log.d(TAG, "run: 名字： " + scanInfo.getAppName());

                    String sourceDir = info.applicationInfo.sourceDir;
                    String md5 = MD5Utils.getFileMd5(sourceDir);
                    String desc = antivirusDao.checkFileVirus(md5);

                    if (TextUtils.isEmpty(desc)) {
                        scanInfo.setVirus(false);
                    } else {
                        scanInfo.setVirus(true);
                        scanInfo.setDesc(desc);
                    }
                    pbScanner.setProgress(progress++);

                    message = Message.obtain();
                    message.what = SCANNING;
                    message.obj = scanInfo;
                    handler.sendMessage(message);
                }
                message = Message.obtain();
                message.what = FINISH;
                handler.sendMessage(message);
            }
        }).start();

    }
}
