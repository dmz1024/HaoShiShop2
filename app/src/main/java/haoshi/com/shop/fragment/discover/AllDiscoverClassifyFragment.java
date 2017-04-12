package haoshi.com.shop.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.AllDiscoverClassifyAdapter;
import haoshi.com.shop.bean.discover.AllDiscoverClassifyBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class AllDiscoverClassifyFragment extends ListNetWorkBaseFragment<AllDiscoverClassifyBean> implements OnTitleBarListener {
    public static AllDiscoverClassifyFragment getInstance(boolean isChoose) {
        AllDiscoverClassifyFragment fragment = new AllDiscoverClassifyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isChoose", isChoose);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean isChoose;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isChoose = bundle.getBoolean("isChoose");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AllDiscoverClassifyAdapter(getContext(), (ArrayList<AllDiscoverClassifyBean.Data>) totalList,isChoose);
    }

    @Override
    protected String url() {
        return ApiConstant.GETTREES;
    }

    @Override
    protected Class<AllDiscoverClassifyBean> getTClass() {
        return AllDiscoverClassifyBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("发现").setOnTitleBarListener(this);
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
