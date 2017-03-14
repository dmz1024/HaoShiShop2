package client.fragment.shop;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import base.fragment.TabIndicatorBaseFragment;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class MyOrderRootFragment extends TabIndicatorBaseFragment {
    @Override
    protected ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MyOrderFragment.getInstance(""));
        fragments.add(MyOrderFragment.getInstance("1"));
        fragments.add(MyOrderFragment.getInstance("2"));
        fragments.add(MyOrderFragment.getInstance("3"));
        fragments.add(MyOrderFragment.getInstance("4"));
        fragments.add(MyOrderFragment.getInstance("5"));
        return fragments;
    }

    @Override
    protected String[] getTabTitles() {
        return new String[]{"全部", "待付款", "待发货", "已发货", "待评价", "已完成"};
    }

    @Override
    protected String getTitleContent() {
        return "我的订单";
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
