package com.hui.mobileguard.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hui.mobileguard.R;
import com.hui.mobileguard.domain.BlackListInfo;

import java.util.List;

/**
 * 须自己实现点击的回调接口
 * Created by HUI on 2016/5/22.
 */
public class BlacklistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String  TAG = "BlackListAdapter";
    private List<BlackListInfo> mBlacklist;
    private OnItemClickListener mlistener;
    private String[] modes = new String[] {"来电拦截+短信拦截","来电拦截","短信拦截"};

    public BlacklistAdapter(  List<BlackListInfo> list) {
        this.mBlacklist = list;
    }

    /*自定义回调接口*/
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_blacklist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        BlackListInfo info = mBlacklist.get(position);
        viewHolder.tvMode.setText(modes[info.getMode()-1]);
        viewHolder.tvNumber.setText(info.getNumber());
    }

    @Override
    public int getItemCount() {
        return mBlacklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNumber;
        public TextView tvMode;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvMode = (TextView) itemView.findViewById(R.id.tv_mode);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mlistener != null) {
                mlistener.onItemClick(v, getAdapterPosition());
            }
        }


    }
}
