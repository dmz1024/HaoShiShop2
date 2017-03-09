package client.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.bean.SerializableMap;
import base.bean.TipLoadingBean;
import base.fragment.ListNetWorkBaseFragment;
import base.fragment.NetworkBaseFragment;
import client.CeshiUrl;
import client.adapter.GoodEvaluateAdapter;
import client.adapter.GoodGroupActivityAdapter;
import client.bean.shop.GoodEvaluateBean;
import client.bean.shop.GoodGroupActivityBean;
import client.constant.ApiConstant;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodGroupActivityFragment extends ListNetWorkBaseFragment<GoodGroupActivityBean> {
    private Map<String, String> filterMap;

    public static GoodGroupActivityFragment getInstance(SerializableMap Sfilter) {
        GoodGroupActivityFragment fragment = new GoodGroupActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Sfilter", Sfilter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            SerializableMap sfilter = (SerializableMap) bundle.getSerializable("Sfilter");
            filterMap = sfilter.getMap();
        }else {
            filterMap=new HashMap<>();
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodGroupActivityAdapter(getContext(), (ArrayList<GoodGroupActivityBean.Data>) totalList);
    }

    @Override
    protected Map<String, String> map() {
        map.putAll(filterMap);
        return super.map();
    }

    @Override
    protected String url() {
        return ApiConstant.GOODS_LIST;
    }

    public void setFileter(Map<String, String> filter) {
        if(filter!=null){
            map.clear();
            filterMap.clear();
            filterMap.putAll(filter);
            getData();
        }
    }

    @Override
    protected boolean getLoadMoreCanShowTipLoading() {
        return true;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("加载中...", "", "");
    }

    @Override
    protected Class<GoodGroupActivityBean> getTClass() {
        return GoodGroupActivityBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }
}
