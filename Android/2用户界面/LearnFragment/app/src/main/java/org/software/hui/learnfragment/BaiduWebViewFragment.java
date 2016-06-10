package org.software.hui.learnfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by wangj on 2015/10/24.
 */
public class BaiduWebViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.baidu_webview, container, false);
        WebView wv = (WebView) root.findViewById(R.id.wv);
        wv.loadUrl("https://www.baidu.com");
        return root;
    }
}
