package com.hui.mobileguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.mobileguard.R;
import com.hui.mobileguard.utils.MD5Utils;

/**
 * Created by HUI on 2016/4/29.
 */
public class HomeActivity extends Activity {
    private static final String TAG = "HomeActivity";
    private GridView gvHome;
    private String[] items = new String[] { "手机防盗","通讯卫士", "软件管理", "进程管理",
            "流量统计", "手机杀毒","缓存管理","高级工具","设置中心" };
    private int[] imgIds = new int[] {R.drawable.home_safe, R.drawable.home_callmsgsafe,
            R.drawable.home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan ,R.drawable.home_sysoptimize, R.drawable.home_tools,
            R.drawable.home_settings };
    private SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gvHome = (GridView) findViewById(R.id.gv_home);
        config = getSharedPreferences("CONFIG", MODE_PRIVATE);

        gvHome.setAdapter(new HomeAdapter());
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showPasswordDialog();
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this, CallSafeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, AppManagerActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this, TastManagerActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(HomeActivity.this, AntivirusActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity.this, ToolsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

        //createShortCut();
    }

    private void showPasswordDialog() {
        /*判断是否已经设置过密码*/
        String password = config.getString("password", null);
        Log.d(TAG, "showPasswordDialog: " + password);
        if (!TextUtils.isEmpty(password)) {
            showPasswordInputDialog();
        } else {
            showPasswordSetDialog();
        }
    }

    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_password_input, null);
        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String savedPassword = config.getString("password",null);
                /*对输入的密码进入判断是否为空是否一致*/
                if (!TextUtils.isEmpty(password)) {
                    if (MD5Utils.encode(password).equals(savedPassword)) {
                        startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HomeActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();

    }

    /*设置密码*/
    private void showPasswordSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_password_set, null);
        //dialog.setView(view,0,0, 0, 0);            //可设置边距
        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();
                /*对输入的密码进入判断是否为空是否一致*/
                if (!TextUtils.isEmpty(password) && !passwordConfirm.isEmpty()) {
                    if (password.equals(passwordConfirm)) {
                        config.edit().putString("password", MD5Utils.encode(password)).commit();
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "再次密码不一致！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();

    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this, R.layout.list_home_item, null);
            ImageView ivHome = (ImageView) view.findViewById(R.id.iv_home);
            TextView tvHome = (TextView) view.findViewById(R.id.tv_home);

            ivHome.setImageResource(imgIds[position]);
            tvHome.setText(items[position]);
            return view;
        }
    }

    private void createShortCut() {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("duplicate", false); //禁止创建多个快捷方式

        /**
         * 1. 图标
         * 2. 命名
         * 3. 做什么
         */
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "软件管理");

        Intent shortcutIntent = new Intent("com.hui.mobileguard.software");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,shortcutIntent);
        sendBroadcast(intent);
    }
}
