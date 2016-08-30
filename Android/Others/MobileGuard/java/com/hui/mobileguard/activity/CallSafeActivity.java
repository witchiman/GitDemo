package com.hui.mobileguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.mobileguard.R;
import com.hui.mobileguard.adapter.BlacklistAdapter;
import com.hui.mobileguard.dao.BlackListDao;
import com.hui.mobileguard.domain.BlackListInfo;
import com.hui.mobileguard.view.RecyclerViewDivider;

import java.util.List;

public class CallSafeActivity extends Activity {

    private static final String TAG = "CallSafeActivity";
    private RelativeLayout rlBlacklist;
    private List<BlackListInfo> mBlacklist;
    private BlacklistAdapter mAdapter;
    private RecyclerView rvBlacklist;
    private BlackListDao dao;
    private Button btnPre;
    private Button btnNext;
    private Button btnJumpTo;
    private EditText etJumpPagge;
    private TextView tvCurrentPage;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_safe);
        rvBlacklist = (RecyclerView) findViewById(R.id.rv_blacklist);
        rlBlacklist = (RelativeLayout) findViewById(R.id.rl_blacklist);

        btnPre = (Button) findViewById(R.id.btn_pre);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnJumpTo = (Button) findViewById(R.id.btn_jumpTo);
        etJumpPagge = (EditText) findViewById(R.id.et_toPage);
        tvCurrentPage = (TextView) findViewById(R.id.tv_currentPage);


        initData();
    }

    private int pageSize = 15;
    private int currentPage = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            rlBlacklist.setVisibility(View.INVISIBLE);
            mAdapter = new BlacklistAdapter(mBlacklist);
            mAdapter.setOnItemClickListener(new BlacklistAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(CallSafeActivity.this, "你点击的是 " + position, Toast.LENGTH_SHORT).show();
                }
            });
            rvBlacklist.setLayoutManager(new LinearLayoutManager(CallSafeActivity.this)); //设置布局

            /*添加分割线*/
            rvBlacklist.addItemDecoration(new RecyclerViewDivider(CallSafeActivity.this,
                    RecyclerViewDivider.VERTICAL_LIST));
            /*rvBlacklist.addItemDecoration(new RecyclerViewDivider(CallSafeActivity.this,//添加自定义的分割线
                     RecyclerViewDivider.VERTICAL_LIST, R.drawable.divider_mileage));*/

            rvBlacklist.setAdapter(mAdapter);
            totalPage = dao.getTotalNumber()/pageSize;
            tvCurrentPage.setText((currentPage) + "/" + totalPage);

        }
    };
    private void initData() {
        dao = new BlackListDao(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //mBlacklist = dao.findAll();
                mBlacklist = dao.findPage(pageSize, currentPage);
                handler.sendEmptyMessage(0);
            }
        }).start();

    }

    public void prePage(View view) {
        if (currentPage <= 1) {
            Toast.makeText(CallSafeActivity.this, "已经是第一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        currentPage--;
        tvCurrentPage.setText(currentPage + "/" + totalPage);
        mBlacklist.clear();
        mBlacklist.addAll(dao.findPage(pageSize, currentPage-1));  //数据库从第0页开始，currentPage-1后才能匹配数据库
        mAdapter.notifyDataSetChanged();

    }

    public void nextPage(View view) {
        if (currentPage >= totalPage) {
            Toast.makeText(CallSafeActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
            return;
        }
         currentPage++;
         tvCurrentPage.setText(currentPage + "/" + totalPage);
         mBlacklist.clear();
         mBlacklist.addAll(dao.findPage(pageSize, currentPage-1));
         Log.d(TAG, "nextPage: page size " + mBlacklist.size());
         mAdapter.notifyDataSetChanged();
    }

    public void jumpToPage(View view) {
        String text = etJumpPagge.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(CallSafeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        } else {
            int pageNumber = Integer.parseInt(text);
            if (pageNumber >= 1 && pageNumber<=totalPage) {
                currentPage = pageNumber;
                tvCurrentPage.setText(currentPage + "/" + totalPage);
                mBlacklist.clear();
                mBlacklist.addAll(dao.findPage(pageSize, currentPage-1));
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(CallSafeActivity.this, "请重新输入", Toast.LENGTH_SHORT).show();
            }
        }
        etJumpPagge.setText("");
    }

}
