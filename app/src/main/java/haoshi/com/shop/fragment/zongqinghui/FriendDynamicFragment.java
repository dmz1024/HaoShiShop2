package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.FriendDynamicAdapter;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshRxBus();
        friendDynamicNoti();
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
                .setRightImage(R.mipmap.zqh_add)
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        RxBus.get().post("addFragment", new AddFragmentBean(new CustomDynamicFragment()));
    }

    @Override
    public void center() {

    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    private Observable<String> friendDynamicNoti;

    private void friendDynamicNoti() {
        if (friendDynamicNoti == null) {
            friendDynamicNoti = RxBus.get().register("friendDynamicNoti", String.class);
            friendDynamicNoti.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    getData();
                }
            });
        }

    }

    private Observable<String> friendDynamicFragment;

    private void initRefreshRxBus() {
        if (friendDynamicFragment == null) {
            friendDynamicFragment = RxBus.get().register("FriendDynamicFragment", String.class);
            friendDynamicFragment.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("friendDynamicNoti",friendDynamicNoti);
        RxBus.get().unregister("FriendDynamicFragment",friendDynamicFragment);
    }
}
