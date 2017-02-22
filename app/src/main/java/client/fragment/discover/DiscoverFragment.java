package client.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.DiscoverAdapter;
import client.bean.DiscoverBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverFragment extends ListNetWorkBaseFragment<DiscoverBean> {
    private String type;

    public static DiscoverFragment getInstance(String type) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new DiscoverAdapter(getContext(), (ArrayList<DiscoverBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "discoves");
        return super.map();
    }

    @Override
    protected Class<DiscoverBean> getTClass() {
        return DiscoverBean.class;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.equals("first", type);
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }
}
