package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.MyOrderAdapter;
import haoshi.com.shop.bean.shop.MyOrderBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class MyOrderFragment extends ListNetWorkBaseFragment<MyOrderBean> {
    private boolean isFirst;

    public static MyOrderFragment getInstance(String type, boolean isFirst) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putBoolean("isFirst", isFirst);
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
            isFirst = bundle.getBoolean("isFirst");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyOrderAdapter(getContext(), (ArrayList<MyOrderBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.WAITPAYBYPAGES;
    }

    @Override
    protected Map<String, String> map() {
        if (!TextUtils.isEmpty(type)) {
            map.put("orderStatus", type);
        }
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
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
        return isFirst;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            ((MyOrderAdapter) mAdapter).cancelRxBus();
        }
    }
}
