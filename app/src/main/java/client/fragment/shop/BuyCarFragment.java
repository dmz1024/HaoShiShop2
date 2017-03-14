package client.fragment.shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.BuyCarAdapter;
import client.bean.shop.BuyCarBean;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class BuyCarFragment extends ListNetWorkBaseFragment<BuyCarBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new BuyCarAdapter(getContext(), (ArrayList<BuyCarBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act","list");
        return super.map();
    }

    @Override
    protected Class<BuyCarBean> getTClass() {
        return BuyCarBean.class;
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
}
