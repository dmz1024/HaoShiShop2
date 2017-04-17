package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.FlockRecommendAdapter;
import haoshi.com.shop.bean.zongqinghui.FlockBean;
import haoshi.com.shop.bean.zongqinghui.SexFlockBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class SexFlockFragment extends ListNetWorkBaseFragment<SexFlockBean> implements OnTitleBarListener {
    public static SexFlockFragment getInstance(String key, String value, String name) {
        SexFlockFragment fragment = new SexFlockFragment();
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        bundle.putString("key", key);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SexFlockFragment getInstance(String name) {
        return getInstance("", "", name);
    }

    private String key;
    private String name;
    private String value;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
            value = bundle.getString("value");
            name = bundle.getString("name");

        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FlockRecommendAdapter(getContext(), (ArrayList<FlockBean>) totalList, 1);
    }

    @Override
    protected String url() {
        return ApiConstant.GROUP_LIST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        if (!TextUtils.isEmpty(key)) {
            map.put(key, value);
        }
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent(name).setOnTitleBarListener(this);
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
