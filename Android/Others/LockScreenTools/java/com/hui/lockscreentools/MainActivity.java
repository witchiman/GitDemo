package com.hui.lockscreentools;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdminSample = new ComponentName(this, MyDeviceAdminReceiver.class);
        /*mDPM.lockNow();  //在onCreate（）里面加入这两行代码可一点应用即可锁屏，做成快捷方式的效果
        finish();*/
    }

    public void lockScreen(View view) {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {  //判断设备管理器是否已经激活
            mDPM.lockNow();
            mDPM.resetPassword("111111",0);         //设置锁屏密码
        }else {
            Toast.makeText(MainActivity.this, "请激活设备管理器", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *激活设备管理器
    */
    public void activateDevice(View view) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "超级一键工具，啦啦，你值得拥有");
        startActivity(intent);
    }

    public void wipeDate(View view) {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {
            mDPM.wipeData(0);
        } else {
            Toast.makeText(MainActivity.this, "请激活设备管理器", Toast.LENGTH_SHORT).show();
        }
    }
    public void unistall(View view) {
        mDPM.removeActiveAdmin(mDeviceAdminSample);

        // 卸载程序
        Intent intent = new Intent(Intent.ACTION_DELETE,Uri.parse("package:" + getPackageName()));
       /* intent.addCategory(Intent.CATEGORY_DEFAULT);         //参考视频敲的没用
        intent.setData(Uri.parse("package:" + getPackageName()));*/
        startActivity(intent);
    }
}
