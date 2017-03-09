package client.fragment.shop;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.GoodAllClassifyAdapter;
import client.adapter.GoodEvaluateAdapter;
import client.bean.shop.GoodAllClassifyBean;
import client.bean.shop.GoodEvaluateBean;
import client.constant.ApiConstant;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodAllClassifyFragment extends ListNetWorkBaseFragment<GoodAllClassifyBean> {

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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("分类");
    }
}
