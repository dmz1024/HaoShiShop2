package haoshi.com.shop.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.FriendDynamicAdapter;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class FriendDynamicFragment extends ListNetWorkBaseFragment<FriendDynamicBean> implements OnTitleBarListener {


    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FriendDynamicAdapter(getContext(), (ArrayList<FriendDynamicBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.FRIENDDYNAMICLIST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
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
}
