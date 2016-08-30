package com.hui.mobileguard.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import com.hui.mobileguard.R;

import java.util.List;

public class TastManagerActivity extends Activity {

    private static final String TAG = "TastManagerActivity";
    private TextView tvMemory;
    private TextView tvProcessNum;
    private ActivityManager activityManager;
    private List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tast_manager);

        tvProcessNum = (TextView) findViewById(R.id.tv_process_num);
        tvMemory = (TextView) findViewById(R.id.tv_memory);

        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        //获取所有运行的进程
        runningAppProcesses = activityManager.getRunningAppProcesses();

        long availMem = memoryInfo.availMem;
        long totalMem = memoryInfo.totalMem;

        tvProcessNum.setText("进程数：" + runningAppProcesses.size());
        tvMemory.setText("剩余/总内存：" + Formatter.formatFileSize(this, availMem) + "/" +
                Formatter.formatFileSize(this, totalMem));


    }
}
