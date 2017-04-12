package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.FriendDynamicAdapter;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class MyFriendDynamicFragment extends ListNetWorkBaseFragment<FriendDynamicBean> implements OnTitleBarListener {
    private String id;

    public static MyFriendDynamicFragment getInstance(String id) {
        MyFriendDynamicFragment fragment = new MyFriendDynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }

    private boolean isInfo;

    @Override
    protected void writeData(boolean isWrite, FriendDynamicBean bean) {
        super.writeData(isWrite, bean);
        if (bean.info != null && dyInfo != null && !isInfo) {
            isInfo = true;
            dyInfo.info(bean.info);
        }
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FriendDynamicAdapter(getContext(), (ArrayList<FriendDynamicBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.USERSETTINGS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", id);
        return super.map();
    }

    @Override
    protected Class<FriendDynamicBean> getTClass() {
        return FriendDynamicBean.class;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("动态")
                .setOnTitleBarListener(this);
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

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }


    public interface MyFriendDyInfo {
        void info(FriendDynamicBean.Info info);
    }


    private MyFriendDyInfo dyInfo;

    public void setDyInfo(MyFriendDyInfo dyInfo) {
        this.dyInfo = dyInfo;
    }

}
