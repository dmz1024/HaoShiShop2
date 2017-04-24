package haoshi.com.shop.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.AllDiscoverCommentAdapter;
import haoshi.com.shop.bean.discover.AllDiscoverCommentBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;
import view.GmRefreshLayout;

/**
 * Created by dengmingzhi on 2017/3/19.
 */

public class DiscoverCommentFragment extends ListNetWorkBaseFragment<AllDiscoverCommentBean> implements OnTitleBarListener {
    public static DiscoverCommentFragment getInstance(String id) {
        return getInstance(id, false);
    }

    public static DiscoverCommentFragment getInstance(String id, boolean type) {
        DiscoverCommentFragment fragment = new DiscoverCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putBoolean("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean type;
    private String id;


    @Override
    protected int getSize() {
        return type ? Integer.MAX_VALUE : super.getSize();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            type = bundle.getBoolean("type");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AllDiscoverCommentAdapter(getContext(), (ArrayList<AllDiscoverCommentBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.REALFULLREVIEWS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", "list");
        map.put("token", "list");
        map.put("goodsId", id);
        return super.map();
    }

    @Override
    protected Class<AllDiscoverCommentBean> getTClass() {
        return AllDiscoverCommentBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("评论")
                .setOnTitleBarListener(this);
    }

    @Override
    protected View getTitleBarView() {
        return type ? null : super.getTitleBarView();
    }

    protected ViewGroup.LayoutParams getParams() {
        return type ? new GmRefreshLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) : super.getParams();
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
