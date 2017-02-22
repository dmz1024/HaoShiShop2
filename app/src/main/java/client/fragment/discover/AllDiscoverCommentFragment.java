package client.fragment.discover;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.AllDiscoverCommentAdapter;
import client.bean.AllDiscoverCommentBean;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class AllDiscoverCommentFragment extends ListNetWorkBaseFragment<AllDiscoverCommentBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AllDiscoverCommentAdapter(getContext(), (ArrayList<AllDiscoverCommentBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "list");
        return super.map();
    }

    @Override
    protected Class<AllDiscoverCommentBean> getTClass() {
        return AllDiscoverCommentBean.class;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("全部评论");
    }

    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }
}
