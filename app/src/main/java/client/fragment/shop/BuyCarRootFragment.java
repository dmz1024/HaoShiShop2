package client.fragment.shop;

import base.fragment.NotNetWorkBaseFragment;
import client.R;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class BuyCarRootFragment extends NotNetWorkBaseFragment {
    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, new BuyCarFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_buy_car_root;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("购物车")
                .setRightContent("编辑");
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
