package com.hui.mobileguard.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hui.mobileguard.R;
import com.hui.mobileguard.domain.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUI on 2016/6/1.
 */
public class AppManagerActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "AppManagerActivity";
    private List<AppInfo> appInfoList;
    private ListView lvAppInfo;

     private TextView tvRom;
     private TextView tvSd;
    private List<AppInfo> userAppList;
    private List<AppInfo> systemAppList;
    private PopupWindow popupWindow;
    private AppInfo itemInfo;
    private UninstallReceiver uninstallReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_app_manager);

        tvRom = (TextView) findViewById(R.id.tv_rom);
        tvSd = (TextView) findViewById(R.id.tv_sd);

        long freeSpace = Environment.getDataDirectory().getFreeSpace();
        tvRom.setText("内存大小：" + Formatter.formatFileSize(this, freeSpace));  //需要格式化
        long sdSpace = Environment.getExternalStorageDirectory().getFreeSpace();
        tvSd.setText("SD大小：" + Formatter.formatFileSize(this, sdSpace));

        appInfoList = getAppInfos();
        userAppList = new ArrayList<AppInfo>();
        systemAppList = new ArrayList<AppInfo>();
        for (AppInfo info : appInfoList) {
            if (info.isUserApp()) {
                userAppList.add(info);
            } else {
                systemAppList.add(info);
            }
        }
        Log.d(TAG, "onCreate: 用户程序 ： " + userAppList.size());
        Log.d(TAG, "onCreate: 系统程序 ： " + systemAppList.size());
        Log.d(TAG, "onCreate: 总程序 ： " + appInfoList.size());

        lvAppInfo = (ListView) findViewById(R.id.lv_app);
        lvAppInfo.setAdapter(new AppInfoAdapter());

        lvAppInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo info;
                if (position>0 && position< userAppList.size()-1){
                    info = userAppList.get(position-1);
                    //Log.d(TAG, info.getAppName() + "是用户程序" + info.isUserApp());
                } else if (position > userAppList.size()+1) {
                    info = systemAppList.get(position-userAppList.size()-2);
                   // Log.d(TAG, info.getAppName() + "是用户程序" + info.isUserApp());
                }
               // Log.d(TAG, "onItemLongClick: " + position);
                return false;
            }
        });

        lvAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = lvAppInfo.getItemAtPosition(position);
                itemInfo = (AppInfo) item;

                if (item!=null && item instanceof AppInfo) {
                    popupWindowDismiss();                                 //避免重复添加
                    View contentView = View.inflate(AppManagerActivity.this, R.layout.popu_window_view, null);

                    int[] location = new int[2];   //获得位置
                    view.getLocationInWindow(location);

                    popupWindow = new PopupWindow(contentView,
                            600, LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popu_window_bg));  //设置背景后添加的动画才能正常显示
                    popupWindow.showAtLocation(parent, Gravity.LEFT + Gravity.TOP, 100, location[1]);

                    ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF,
                            Animation.RELATIVE_TO_SELF);
                    animation.setDuration(300);
                    contentView.startAnimation(animation);

                    contentView.findViewById(R.id.tv_run).setOnClickListener(AppManagerActivity.this);
                    contentView.findViewById(R.id.tv_detail).setOnClickListener(AppManagerActivity.this);
                    contentView.findViewById(R.id.tv_share).setOnClickListener(AppManagerActivity.this);
                    contentView.findViewById(R.id.tv_uninstall).setOnClickListener(AppManagerActivity.this);
                }
            }
        });

        lvAppInfo.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    popupWindowDismiss();
            }
        });

        /*注册卸载应用的广播*/
        uninstallReceiver = new UninstallReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(uninstallReceiver,filter);

    }

    private class UninstallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: 卸载了");
        }
    }

    /*获取应用程序列表*/
    private List<AppInfo> getAppInfos() {
        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(0);//获取安装包
        List<AppInfo> appList =  new ArrayList<AppInfo>();
        for (PackageInfo info : installedPackages) {
            ApplicationInfo applicationInfo = info.applicationInfo;
            Drawable appLogo = applicationInfo.loadIcon(getPackageManager());    //获取应用程序图标

            String appName = applicationInfo.loadLabel(getPackageManager()).toString();  //获取应用程序的名字
            String sourceDir = applicationInfo.sourceDir;                    //获取应用程序的目录
            File file = new File(sourceDir);
            long appSize = file.length();
            //Log.d(TAG, "getAppInfos: " + appName + "|"+ appSize + "|");
            int flags = applicationInfo.flags;              //获取应用程序的标记

            String packageName = applicationInfo.packageName;

            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(appName);
            appInfo.setAppLogo(appLogo);
            appInfo.setAppSize(appSize);
            appInfo.setPackageName(packageName);


            if ((flags&ApplicationInfo.FLAG_SYSTEM) != 0) {     //通过标记判断是否为系统应用
                appInfo.setUserApp(false);
            } else {
                appInfo.setUserApp(true);
            }

            if ((flags&applicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {  //通过标记判断存储位置
                appInfo.setInRom(true);
            } else {
                appInfo.setInRom(false);
            }
            appList.add(appInfo);
        }
        return appList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_run:             //运行应用
                //Log.d(TAG, "onClick: APP NAME " + itemInfo.getAppName());
                Intent runIntent = getPackageManager().getLaunchIntentForPackage(itemInfo.getPackageName());
                startActivity(runIntent);
                popupWindowDismiss();
                break;
            case R.id.tv_detail:
                Intent detailIntent = new Intent();
                detailIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                detailIntent.addCategory(Intent.CATEGORY_DEFAULT);
                detailIntent.setData(Uri.parse("package:" + itemInfo.getPackageName()));
                startActivity(detailIntent);
                popupWindowDismiss();
                break;
            case R.id.tv_share:
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.SUBJECT", "我的分享");
                shareIntent.putExtra("android.intent.extra.TEXT", "推荐你使用" + itemInfo.getAppName() + "，下载地址："
                    + "https://play.google.com/store/apps/details?id=" + itemInfo.getPackageName());   //加入链接可直接打开安装的应用商店，找到应用
                startActivity(Intent.createChooser(shareIntent, "分享"));
                popupWindowDismiss();
                break;
            case R.id.tv_uninstall:
                Intent uninstallIntent = new Intent();
                uninstallIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                uninstallIntent.addCategory(Intent.CATEGORY_DEFAULT);
                uninstallIntent.setData(Uri.parse("package:"+itemInfo.getPackageName()));
                startActivity(uninstallIntent);
                popupWindowDismiss();
                break;
            default:
                break;
        }
    }

    private class AppInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return appInfoList.size() + 2;
        }

        /*返回正确的AppInfo*/
        @Override
        public AppInfo getItem(int position) {
            if (position>0 && (position < userAppList.size() + 1)) {
                return userAppList.get(position-1);
            } else if (position > userAppList.size() + 1 ) {
                return systemAppList.get(position-userAppList.size()-2);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                TextView tvUser = new TextView(AppManagerActivity.this);
                tvUser.setTextSize(18);
                tvUser.setText("用户程序(" + userAppList.size() + ")");
                return tvUser;
            } else if( position == userAppList.size() + 1) {
                TextView tvSystem = new TextView(AppManagerActivity.this);
                tvSystem.setTextSize(18);
                tvSystem.setText("系统程序（" + systemAppList.size() + ")");
                return tvSystem;
            }

            ViewHolder holder = null;
            AppInfo info = getItem(position);

            if (convertView != null && convertView instanceof LinearLayout) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(AppManagerActivity.this, R.layout.list_app_item, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_app_name);
                holder.tvSize = (TextView) convertView.findViewById(R.id.tv_app_size);
                holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_app_location);
                holder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_app_logo);
                convertView.setTag(holder);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.ivLogo.setBackground(info.getAppLogo());
            }
            holder.tvName.setText(info.getAppName());
            holder.tvSize.setText(Formatter.formatFileSize(AppManagerActivity.this, info.getAppSize())); //需要格式化
            if (info.isInRom()) {
                holder.tvLocation.setText("手机内存");
            } else {
                holder.tvLocation.setText("SD卡");
            }
            return convertView;
        }

        class ViewHolder {
            ImageView ivLogo;
            TextView tvName;
            TextView tvSize;
            TextView tvLocation;
        }
    }

    private void popupWindowDismiss() {
        if (popupWindow!=null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    protected void onDestroy() {
        popupWindowDismiss();
        unregisterReceiver(uninstallReceiver);
        super.onDestroy();
    }
}
