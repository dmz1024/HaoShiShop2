package haoshi.com.shop.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.ChatFriendAdapter;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.zongqinghui.StandFriendBean;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/6.
 * 站内好友
 */

public class StandFriendFragment extends ListNetWorkBaseFragment<StandFriendBean> implements OnTitleBarListener {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ChatFriendAdapter(getContext(), (ArrayList<ChatFriendBean>) totalList, false);
    }

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "list");
        return super.map();
    }

    @Override
    protected Class<StandFriendBean> getTClass() {
        return StandFriendBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("站内好友").setOnTitleBarListener(this);
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
