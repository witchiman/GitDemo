package com.hui.listview_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by HUI on 2016/1/16.
 */
public class MsgAdapter extends ArrayAdapter<Msg> {
    private int resourceId;

    public MsgAdapter(Context context, int resource, List<Msg> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg = getItem(position);
        View view;
        MsgHolder msgHolder;

        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            msgHolder = new MsgHolder();
            msgHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            msgHolder.tvLeft = (TextView) view.findViewById(R.id.left_text);
            msgHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            msgHolder.tvRight = (TextView) view.findViewById(R.id.right_text);
            view.setTag(msgHolder);
        } else {
            view = convertView;
            msgHolder = (MsgHolder) view.getTag();
        }

        if(msg.getType() == Msg.TYPE_RECIEVE) {
            msgHolder.leftLayout.setVisibility(View.VISIBLE);
            msgHolder.rightLayout.setVisibility(View.GONE);
            msgHolder.tvLeft.setText(msg.getContent());
        }else {
            msgHolder.rightLayout.setVisibility(View.VISIBLE);
            msgHolder.leftLayout.setVisibility(View.GONE);
            msgHolder.tvRight.setText(msg.getContent());
        }

        return view;
    }

    private class MsgHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView tvLeft;
        TextView tvRight;
    }
}
