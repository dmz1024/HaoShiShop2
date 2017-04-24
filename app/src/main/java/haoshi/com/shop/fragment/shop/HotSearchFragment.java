package haoshi.com.shop.fragment.shop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.DiscoverFillterAdapter;
import haoshi.com.shop.bean.shop.HotSearchBean;
import haoshi.com.shop.constant.ApiConstant;

/**
 * Created by dengmingzhi on 2017/4/19.
 */

public class HotSearchFragment extends ListNetWorkBaseFragment<HotSearchBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new DiscoverFillterAdapter(getContext(), (ArrayList<String>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.HOTSEARCH;
    }

    @Override
    protected Class<HotSearchBean> getTClass() {
        return HotSearchBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }



    @Override
    protected View getNoDataView() {
        return View.inflate(getContext(), com.mall.naiqiao.mylibrary.R.layout.view_no_data, null);
    }

    @Override
    protected LinearLayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
