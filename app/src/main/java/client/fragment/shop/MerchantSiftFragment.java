package client.fragment.shop;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.GoodEvaluateAdapter;
import client.adapter.MerchantSiftAdapter;
import client.bean.shop.GoodEvaluateBean;
import client.bean.shop.MerchantSiftBean;
import client.constant.ApiConstant;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class MerchantSiftFragment extends ListNetWorkBaseFragment<MerchantSiftBean> {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MerchantSiftAdapter(getContext(), (ArrayList<MerchantSiftBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.MERCHANT_SIFT;
    }


    @Override
    protected Class<MerchantSiftBean> getTClass() {
        return MerchantSiftBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("商家精选");
    }
}
