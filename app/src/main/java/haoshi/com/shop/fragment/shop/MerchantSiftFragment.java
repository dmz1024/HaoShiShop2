package haoshi.com.shop.fragment.shop;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.MerchantSiftAdapter;
import haoshi.com.shop.bean.shop.MerchantSiftBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class MerchantSiftFragment extends ListNetWorkBaseFragment<MerchantSiftBean> implements OnTitleBarListener {

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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("商家精选").setOnTitleBarListener(this);
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
