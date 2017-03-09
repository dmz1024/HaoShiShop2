package client.fragment.chat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import api.TestConstant;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import client.R;
import client.adapter.ChatFriendAdapter;
import client.adapter.ChatFriendGroupAdapter;
import client.bean.chat.dao.ChatFriendGroupBean;
import client.bean.chat.impl.ChatFriendGroupImpl;
import client.bean.chat.impl.ChatFriendsImpl;
import client.bean.chat.dao.ChatFriendBean;
import interfaces.OnTitleBarListener;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ChatFriendFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private ArrayList<ChatFriendGroupBean> datas;
    private ChatFriendGroupAdapter mAdapter;


    @Override
    protected void initData() {
        datas = ChatFriendGroupImpl.getInstance(getContext()).getDatas();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatFriendGroupAdapter(getContext(), datas) {
            @Override
            protected void edit(int layoutPosition) {
                ChatFriendsImpl.getInstance(getContext()).add(new ChatFriendBean(System.currentTimeMillis() + "", "好友" , TestConstant.IMAGE, "好友+", datas.get(layoutPosition).getGid(), 1));
                datas = ChatFriendGroupImpl.getInstance(getContext()).getDatas();
                notifyDataSetChanged();
            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getRId() {
        return R.layout.recycle_view;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("").setRightContent("添加").setOnTitleBarListener(this);
    }

    @Override
    public void left() {

    }

//    int count = 0;

    @Override
    public void right() {
        ChatFriendGroupImpl.getInstance(getContext()).add(new ChatFriendGroupBean(System.currentTimeMillis() + "", "1", "分组"));
        datas = ChatFriendGroupImpl.getInstance(getContext()).getDatas();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void center() {

    }

//    @Override
//    protected View getTitleBarView() {
//        return null;
//    }


    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }
}
