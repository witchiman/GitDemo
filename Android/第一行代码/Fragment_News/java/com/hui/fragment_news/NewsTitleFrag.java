package com.hui.fragment_news;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUI on 2016/1/16.
 */
public class NewsTitleFrag extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lv;
    private NewsAdapter adapter;
    private List<News> newsList;
    private boolean isTwoPane;
    public final static  String TAG = "NewsTitileFrag";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initNews();
        adapter = new NewsAdapter(context,R.layout.news_item,newsList);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initNews();
        adapter = new NewsAdapter(activity,R.layout.news_item,newsList);
        Log.d(TAG, "onAttach");
    }

    public void initNews() {
        newsList = new ArrayList<News>();
        News news1 = new News("给亚投行项目准备5000万美元","新华社北京1月16日电（记者郝亚琳、韩洁、白洁）1月16日上午，亚洲基础设施投资银行开业仪式在北京钓鱼台国宾馆举行。中国国家主席习近平 出席开业仪式并致辞，强调通过各成员国携手努力，亚投行一定能成为专业、高效、廉洁的21世纪新型多边开发银行，成为构建人类命运共同体的新平台，为促进 亚洲和世界发展繁荣作出新贡献，为改善全球经济治理增添新力量。");
        News news2 = new News("中国正式加入欧洲复兴开发银行","李克强强调，当前中国经济运行总体平稳，增长速度在世界主要经济体中名列前茅，外汇储备充裕，金融体系稳健。中国无意通过货币竞争性贬值刺激出口，人民币汇率也不存在持续贬值的基础。中国有能力继续保持人民币汇率在合理均衡水平上的基本稳定。\n" +
                "\n" +
                "　　查克拉巴蒂表示，过去30多年中国经济社会发展取得举世公认的成就，也为国际发展合作提供了有益借鉴。欧洲复兴开发银行相信中国政府对经济政策的把握，愿以中国正式加入为契机，积极推动欧中发展战略对接，深入推进各项合作倡议，加强同中国政府、企业界的沟通，以及在亚洲基础设施投资银行、二十国集团等多边金融机构和平台的合作，构建强有力的伙伴关系。");
        newsList.add(news1);
        newsList.add(news2);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lv = (ListView) view.findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        Log.d(TAG,"onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;
        }else {
            isTwoPane = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = newsList.get(position);
        if(isTwoPane == true) {
            NewsContentFrag newsContentFrag = (NewsContentFrag) getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFrag.refresh(news.getTitle(),news.getContent());
        } else {
            NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
        }
    }
}
