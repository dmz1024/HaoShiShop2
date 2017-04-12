package haoshi.com.shop.fragment.shop;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.GoodAllClassifyAdapter;
import haoshi.com.shop.bean.shop.GoodAllClassifyBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodAllClassifyFragment extends ListNetWorkBaseFragment<GoodAllClassifyBean> implements OnTitleBarListener {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodAllClassifyAdapter(getContext(), (ArrayList<GoodAllClassifyBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.GOOD_ALL_CLASSIFY;
    }


    @Override
    protected Class<GoodAllClassifyBean> getTClass() {
        return GoodAllClassifyBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("分类").setOnTitleBarListener(this);
    }


    @Override
    protected boolean getLoadMore() {
        return false;
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
