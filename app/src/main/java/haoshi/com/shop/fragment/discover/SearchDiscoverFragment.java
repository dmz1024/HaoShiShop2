package haoshi.com.shop.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.DiscoverAdapter;
import haoshi.com.shop.bean.discover.DiscoverBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class SearchDiscoverFragment extends ListNetWorkBaseFragment<DiscoverBean> implements OnTitleBarListener {
    private String catId;
    private String name;

    public static SearchDiscoverFragment getInstance(String catId,String name) {
        SearchDiscoverFragment fragment = new SearchDiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString("catId", catId);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            catId = bundle.getString("catId");
            name = bundle.getString("name");
        }
    }


    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new DiscoverAdapter(getContext(), (ArrayList<DiscoverBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.LISTQUERY;
    }

    @Override
    protected Map<String, String> map() {
        map.put("catId", catId);
        return super.map();
    }

    @Override
    protected Class<DiscoverBean> getTClass() {
        return DiscoverBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent(name).setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back","back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
