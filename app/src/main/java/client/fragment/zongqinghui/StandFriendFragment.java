package client.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.ChatFriendAdapter;
import client.bean.FriendBean;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.zongqinghui.StandFriendBean;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/6.
 * 站内好友
 */

public class StandFriendFragment extends ListNetWorkBaseFragment<StandFriendBean> {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ChatFriendAdapter(getContext(), (ArrayList<ChatFriendBean>) totalList);
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("站内好友");
    }
}
