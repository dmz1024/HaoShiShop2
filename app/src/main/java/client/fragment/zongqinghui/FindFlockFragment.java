package client.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.FindFlockAdapter;
import client.bean.zongqinghui.FindFlockBean;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFlockFragment extends ListNetWorkBaseFragment<FindFlockBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FindFlockAdapter(getContext(), (ArrayList<FindFlockBean.Data>) totalList);
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
    protected Class<FindFlockBean> getTClass() {
        return FindFlockBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
