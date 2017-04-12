package haoshi.com.shop.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.DiscoverAdapter;
import haoshi.com.shop.adapter.DiscoverSendAdapter;
import haoshi.com.shop.bean.discover.DiscoverBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class MyDiscoverSendFragment extends ListNetWorkBaseFragment<DiscoverBean> implements OnTitleBarListener {

    public static MyDiscoverSendFragment getInstance(boolean isSend) {
        MyDiscoverSendFragment fragment = new MyDiscoverSendFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSend", isSend);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isSend = bundle.getBoolean("isSend");
        }
    }

    private boolean isSend;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new DiscoverSendAdapter(getContext(), (ArrayList<DiscoverBean.Data>) totalList,isSend);
    }


    @Override
    protected int time() {
        return isSend?500:super.time();
    }

    @Override
    protected String url() {
        return ApiConstant.MYARTICLES;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<DiscoverBean> getTClass() {
        return DiscoverBean.class;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("我已发布")
                .setOnTitleBarListener(this);
        if(!isSend){
            ((DefaultTitleBarView) getTitleBar()).setRightContent("编辑");
        }
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    private boolean isEdit;

    @Override
    public void right() {
        if (totalList.size() == 0) {
            return;
        }
        isEdit = !isEdit;
        ((DefaultTitleBarView) getTitleBar()).setRightContent(isEdit ? "取消编辑" : "编辑");
        ((DiscoverSendAdapter) mAdapter).setDelete(isEdit);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void center() {

    }
}
