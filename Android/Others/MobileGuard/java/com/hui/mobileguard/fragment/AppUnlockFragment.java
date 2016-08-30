package com.hui.mobileguard.fragment;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hui.mobileguard.R;
import com.hui.mobileguard.dao.AppLockDao;
import com.hui.mobileguard.domain.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUI on 2016/6/12.
 */
public class AppUnlockFragment extends Fragment {

    private static final String TAG = "AppUnlockFragment";
    private TextView tvUnlcok;
    private ListView lvUnlock;
    private List<PackageInfo> installedPackages;
    private AppUnlockAdapter adapter;
    private List<AppInfo> appInfoList;
    private AppLockDao dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_unlock, null);
        tvUnlcok = (TextView) view.findViewById(R.id.tv_unlock);
        lvUnlock = (ListView) view.findViewById(R.id.lv_unlock);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        PackageManager manager = getActivity().getPackageManager();
        installedPackages = manager.getInstalledPackages(0);
        appInfoList = new ArrayList<AppInfo>();

        dao = new AppLockDao(getActivity());

        for (PackageInfo packageInfo : installedPackages) {
            AppInfo appInfo = new AppInfo();

            if (dao.find(packageInfo.packageName)) { //如果发现本地数据库含有包名则说明已经加锁直接跳过
                continue;
            }

            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(getActivity().getPackageManager())
                    .toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setAppLogo(packageInfo.applicationInfo.loadIcon(getActivity().getPackageManager()));
            appInfoList.add(appInfo);
        }

        adapter = new AppUnlockAdapter();
        lvUnlock.setAdapter(adapter);

        tvUnlcok.setText("未加锁(" + appInfoList.size() + ")" );
    }

    class AppUnlockAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return appInfoList.size();
        }
        @Override
        public AppInfo getItem(int position) {
            return appInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final AppInfo appInfo = getItem(position);
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_unlock, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.ivLock = (ImageView) convertView.findViewById(R.id.iv_unlock);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ivIcon.setImageDrawable(appInfo.getAppLogo());
            holder.tvName.setText(appInfo.getAppName());
            final View finalConvertView = convertView;
            holder.ivLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: 删除了" + appInfo.getPackageName());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dao.add(appInfo.getPackageName());
                                    appInfoList.remove(position);
                                    tvUnlcok.setText("未加锁(" + appInfoList.size() + ")" );
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();

                    TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    animation.setDuration(1000);
                    finalConvertView.startAnimation(animation);

                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView tvName;
            ImageView ivIcon;
            ImageView ivLock;

        }
    }
}
