package client.bean.zongqinghui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.FlockRecommendAdapter;
import client.bean.FlockBean;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class SexFlockFragment extends ListNetWorkBaseFragment<SexFlockBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FlockRecommendAdapter(getContext(), (ArrayList<FlockBean>) totalList, 1);
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
    protected Class<SexFlockBean> getTClass() {
        return SexFlockBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("性别相关");
    }
}
