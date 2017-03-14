package client.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.MyOrderAdapter;
import client.bean.shop.MyOrderBean;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class MyOrderFragment extends ListNetWorkBaseFragment<MyOrderBean> {

    public static MyOrderFragment getInstance(String type) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String type;

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
        return new MyOrderAdapter(getContext(), (ArrayList<MyOrderBean.Data>) totalList);
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
    protected Class<MyOrderBean> getTClass() {
        return MyOrderBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return true;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.isEmpty(type);
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
