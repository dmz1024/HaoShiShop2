package client.fragment.shop;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.GoodEvaluateAdapter;
import client.bean.shop.GoodEvaluateBean;
import client.constant.ApiConstant;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodEvaluateFragment extends ListNetWorkBaseFragment<GoodEvaluateBean> {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodEvaluateAdapter(getContext(), (ArrayList<GoodEvaluateBean.Data>) totalList);
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
    protected Class<GoodEvaluateBean> getTClass() {
        return GoodEvaluateBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("全部评价");
    }
}
