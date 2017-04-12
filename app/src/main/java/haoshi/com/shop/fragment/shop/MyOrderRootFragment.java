package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import base.fragment.TabIndicatorBaseFragment;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class MyOrderRootFragment extends TabIndicatorBaseFragment {
    private int current = 0;

    public static MyOrderRootFragment getInstance(int current) {
        MyOrderRootFragment fragment = new MyOrderRootFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("current", current);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            current = bundle.getInt("current");
        }
    }

    @Override
    protected ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] types = {"", "16", "9", "6", "12","10","11", "7"};
        for (int i = 0; i < types.length; i++) {
            fragments.add(MyOrderFragment.getInstance(types[i], current == i));
        }
        return fragments;
    }

    @Override
    protected String[] getTabTitles() {
        return new String[]{"全部", "待付款", "待发货", "已发货", "待评价","退款中","已退款", "已完成"};
    }

    @Override
    protected String getTitleContent() {
        return "我的订单";
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public int getCurrent() {
        return current;
    }


}
