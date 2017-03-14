package client.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.GoodEvaluateAdapter;
import client.bean.shop.GoodEvaluateBean;
import client.constant.ApiConstant;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodEvaluateFragment extends ListNetWorkBaseFragment<GoodEvaluateBean> {
    private boolean isCanFirst = true;
    private String shopId;

    public static GoodEvaluateFragment getInstance(String shopId, boolean isCanFirst) {
        GoodEvaluateFragment fragment = new GoodEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("shopId", shopId);
        bundle.putBoolean("isCanFirst", isCanFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GoodEvaluateFragment getInstance(String shopId) {
        return getInstance(shopId, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isCanFirst = bundle.getBoolean("isCanFirst", true);
            shopId = bundle.getString("shopId");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodEvaluateAdapter(getContext(), (ArrayList<GoodEvaluateBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.GOOD_EVALUATE;
    }

    @Override
    protected Map<String, String> map() {
        map.put("shopId", shopId);
        return super.map();
    }

    @Override
    protected Class<GoodEvaluateBean> getTClass() {
        return GoodEvaluateBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("全部评价");
    }

    @Override
    protected boolean isCanFirstInitData() {
        return isCanFirst;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return true;
    }

    @Override
    protected View getTitleBarView() {
        return isCanFirst ? super.getTitleBarView() : null;
    }
}
