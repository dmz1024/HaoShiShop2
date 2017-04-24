package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.FindFlockAdapter;
import haoshi.com.shop.bean.zongqinghui.FindFlockBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFlockFragment extends ListNetWorkBaseFragment<FindFlockBean> {
    private boolean isCanFirst;

    public static FindFlockFragment getInstance(boolean isCanFirst) {
        FindFlockFragment fragment = new FindFlockFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCanFirst", isCanFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isCanFirst = bundle.getBoolean("isCanFirst");
        }
    }

    @Override
    protected boolean isCanFirstInitData() {
        return isCanFirst;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FindFlockAdapter(getContext(), (ArrayList<FindFlockBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.FIND_GROUP;
    }


    @Override
    protected void writeData(boolean isWrite, FindFlockBean bean) {
        super.writeData(isWrite, bean);
        if (bean.user != null) {
            if (onUserInfoInterface != null) {
                onUserInfoInterface.user(bean.user);
            }
        }
    }

    private OnUserInfoInterface onUserInfoInterface;

    public void setOnUserInfoInterface(OnUserInfoInterface onUserInfoInterface) {
        this.onUserInfoInterface = onUserInfoInterface;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<FindFlockBean> getTClass() {
        return FindFlockBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }
    @Override
    protected boolean isOnlyInitOne() {
        return false;
    }

}
