package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.GoodEvaluateAdapter;
import haoshi.com.shop.bean.shop.GoodEvaluateBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodEvaluateFragment extends ListNetWorkBaseFragment<GoodEvaluateBean> implements OnTitleBarListener {
    private boolean isCanFirst = true;
    private String id;

    public static GoodEvaluateFragment getInstance(String id, boolean isCanFirst) {
        GoodEvaluateFragment fragment = new GoodEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putBoolean("isCanFirst", isCanFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GoodEvaluateFragment getInstance(String id) {
        return getInstance(id, true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isCanFirst = bundle.getBoolean("isCanFirst", true);
            id = bundle.getString("id");
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
        map.put(id.split(",")[0], id.split(",")[1]);
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("全部评价").setOnTitleBarListener(this);
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



    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
