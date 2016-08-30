package com.hui.mobileguard.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.hui.mobileguard.R;
import com.hui.mobileguard.service.CallerIDService;
import com.hui.mobileguard.service.RocketService;
import com.hui.mobileguard.view.SettingItemClick;
import com.hui.mobileguard.view.SettingItemView;

import java.util.List;

/**
 * Created by HUI on 2016/5/2.
 */
public class SettingActivity extends Activity {

    private static final String TAG = "SettingActivity";
    private SettingItemView sivAutoUpdate;
    private  SharedPreferences config;
    private boolean isAutoUpdate;
    private SettingItemView sivCallerID;
    private SettingItemClick sicStyle;
    private String[] items;
    private int style;
    private SettingItemClick sicLocation;
    private SettingItemView sivRocket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdateView();
        initCallerIDView();
        initAddressStyleSettingView();
        initAddressLocationView();
        initRocketView();
    }

    /*初始化自动升级的设置选项*/
    private void initUpdateView() {
        sivAutoUpdate = (SettingItemView) findViewById(R.id.siv_autoupdate);
        config = getSharedPreferences("CONFIG", Context.MODE_PRIVATE);

        isAutoUpdate = config.getBoolean("auto_update",true);
        //sivAutoUpdate.setTitle("自动更新");
        if (isAutoUpdate) {
            //sivAutoUpdate.setDesciption("自动更新已经打开");
            sivAutoUpdate.setStatus(true);
        } else {
            //sivAutoUpdate.setDesciption("自动更新已经关闭");
            sivAutoUpdate.setStatus(false);
        }

        sivAutoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivAutoUpdate.isChecked()) {
                    // sivAutoUpdate.setDesciption("自动更新已经关闭");
                    sivAutoUpdate.setStatus(false);
                    config.edit().putBoolean("auto_update",false).commit();
                } else {
                    //sivAutoUpdate.setDesciption("自动更新已经打开");
                    sivAutoUpdate.setStatus(true);
                    config.edit().putBoolean("auto_update",true).commit();
                }
            }
        });
    }

    /*初始化来电显示的设置选项*/
    private void initCallerIDView() {
        sivCallerID = (SettingItemView) findViewById(R.id.siv_caller_id);
        boolean isRunning = isServiceRunning("com.hui.mobileguard.service.CallerIDService");
        if (isRunning) {
            sivCallerID.setStatus(true);
        } else {
            sivCallerID.setStatus(false);
        }

        sivCallerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivCallerID.isChecked()) {
                    sivCallerID.setStatus(false);
                    stopService(new Intent(SettingActivity.this, CallerIDService.class));
                } else {
                    sivCallerID.setStatus(true);
                    startService(new Intent(SettingActivity.this, CallerIDService.class));
                }
            }
        });
    }

    /*判断是否开启来电显示的服务*/
    private boolean isServiceRunning(String serviceName) {
        ActivityManager am  = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);//最多返回100个服务
        for (ActivityManager.RunningServiceInfo serviceInfo: runningServices) {
            String name = serviceInfo.service.getClassName();
            if (name.equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    private void initAddressStyleSettingView() {
        sicStyle = (SettingItemClick) findViewById(R.id.sic_address_style);
        sicStyle.setTitle("归属地提示框风格");
        items = new String[] {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
        style = config.getInt("style", 0);
        sicStyle.setDesciption(items[style]);
        sicStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStyleChooseDialog();
            }
        });

    }

    private void showStyleChooseDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("归属地提示框风格");
        builder.setSingleChoiceItems(items, style, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                config.edit().putInt("style", which).commit();
                sicStyle.setDesciption(items[which]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    /**
     * 初始化的来电提示框的位置设置选项
     */
    private void initAddressLocationView() {
        sicLocation = (SettingItemClick) findViewById(R.id.sic_address_location);
        sicLocation.setTitle("归属地提示框显示位置");
        sicLocation.setDesciption("设置归属地提示框的显示位置");
        sicLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, DragActivity.class));
            }
        });
    }

    /**
     * 火箭工具设置
     */
    private void initRocketView() {
        sivRocket = (SettingItemView) findViewById(R.id.siv_rocket);
        boolean isShow = isServiceRunning("com.hui.mobileguard.service.RocketService");
        if (isShow) {
            sivRocket.setStatus(true);
        } else {
            sivRocket.setStatus(false);
        }

        sivRocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivRocket.isChecked()) {
                    sivRocket.setStatus(false);
                    stopService(new Intent(SettingActivity.this, RocketService.class));
                } else {
                    sivRocket.setStatus(true);
                    startService(new Intent(SettingActivity.this, RocketService.class));
                }
            }
        });
    }
}
