package com.hui.mobileguard.fragment;

import android.app.Fragment;
import android.content.pm.ApplicationInfo;
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
public class AppLockFragment extends Fragment {

    private static final String TAG = "AppLockFragment";
    private TextView tvLcok;
    private ListView lvLock;
    private AppLockDao dao;
    private List<AppInfo> appInfoList;
    private AppLockAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_lock, null);
        tvLcok = (TextView) view.findViewById(R.id.tv_lock);
        lvLock = (ListView) view.findViewById(R.id.lv_lock);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        PackageManager packageManager = getActivity().getPackageManager();
        List<ApplicationInfo> applications = packageManager.getInstalledApplications(0);  //getInstallApplications貌似与getInstallPacked无啥大区别
         appInfoList = new ArrayList<AppInfo>();

        dao = new AppLockDao(getActivity());
        List<String> packageNameList = dao.findAll();

        for (ApplicationInfo applicationInfo : applications) {
            if (packageNameList.contains(applicationInfo.packageName)) {
                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(applicationInfo.loadLabel(getActivity().getPackageManager()).toString());
                appInfo.setAppLogo(applicationInfo.loadIcon(getActivity().getPackageManager()));
                appInfo.setPackageName(applicationInfo.packageName);
                appInfoList.add(appInfo);
            }
        }

        adapter = new AppLockAdapter();
        lvLock.setAdapter(adapter);
        tvLcok.setText("加锁(" + appInfoList.size() + ")");

    }

    class AppLockAdapter extends BaseAdapter {

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
            ViewHolder holder  = null;
            final AppInfo appInfo = getItem(position);

            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_lock, null);
                holder = new ViewHolder();
                holder.tvName  = (TextView) convertView.findViewById(R.id.tv_name);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.ivLock = (ImageView) convertView.findViewById(R.id.iv_lock);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(appInfo.getAppName());
            holder.ivIcon.setImageDrawable(appInfo.getAppLogo());
            final View finalConvertView = convertView;
            holder.ivLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dao.delete(appInfo.getPackageName());
                                    Log.d(TAG, "onClick: 解锁了" + appInfo.getAppName());
                                    appInfoList.remove(position);
                                    tvLcok.setText("加锁(" + appInfoList.size() + ")");

                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();

                    TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                            0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF,0 ,Animation.RELATIVE_TO_SELF, 0);
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
