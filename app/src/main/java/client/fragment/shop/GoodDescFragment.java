package client.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Map;

import base.fragment.SingleNetWorkBaseFragment;
import client.CeshiUrl;
import client.R;
import client.bean.shop.GoodDescBean;
import client.constant.ApiConstant;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/7.
 */

public class GoodDescFragment extends SingleNetWorkBaseFragment<GoodDescBean> {
    private String good_id;

    public static GoodDescFragment getInstance(String good_id) {
        GoodDescFragment fragment = new GoodDescFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", good_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            good_id = bundle.getString("id");
        }
    }

    @Override
    protected String url() {
        return ApiConstant.GOOD_DESC;
    }

    @Override
    protected Map<String, String> map() {
        map.put("goodsId", good_id);
        return super.map();
    }

    @Override
    protected Class<GoodDescBean> getTClass() {
        return GoodDescBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_good_desc, null);
        return view;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("商品详情").setRightImage(R.mipmap.fenxiang);
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
