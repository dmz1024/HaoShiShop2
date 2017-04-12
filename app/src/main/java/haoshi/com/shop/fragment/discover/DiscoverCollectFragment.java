package haoshi.com.shop.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.DiscoverCollectAdapter;
import haoshi.com.shop.bean.discover.DiscoverBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverCollectFragment extends ListNetWorkBaseFragment<DiscoverBean> {
    private String catId;
    private boolean isFirst;

    public static DiscoverCollectFragment getInstance(String catId, boolean isFirst) {
        DiscoverCollectFragment fragment = new DiscoverCollectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("catId", catId);
        bundle.putBoolean("isFirst", isFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            catId = bundle.getString("catId");
            isFirst = bundle.getBoolean("isFirst");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new DiscoverCollectAdapter(getContext(), (ArrayList<DiscoverBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.MYBROWSINGS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("catId", catId);
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<DiscoverBean> getTClass() {
        return DiscoverBean.class;
    }

//    @Override
//    protected boolean isCanRefresh() {
//        return false;
//    }


    @Override
    protected boolean isOnlyInitOne() {
        return true;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return isFirst;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    public void showDelete(boolean isDelete) {
        if (mAdapter != null) {
            ((DiscoverCollectAdapter) mAdapter).setDelete(isDelete);
        }
    }
}
