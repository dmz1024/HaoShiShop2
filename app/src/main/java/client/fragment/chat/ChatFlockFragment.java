package client.fragment.chat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import api.TestConstant;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import client.R;
import client.adapter.ChatFriendAdapter;
import client.adapter.ChatMessageAdapter;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.chat.dao.ChatMessageBean;
import client.bean.chat.impl.ChatFriendsImpl;
import client.bean.chat.impl.ChatMessagesImpl;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ChatFlockFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private ArrayList<ChatFriendBean> datas;
    ChatFriendAdapter mAdapter;

    int count = 0;

    @Override
    protected void initData() {
        datas = ChatFriendsImpl.getInstance(getContext()).getDatas();
        count = datas.size() + 100;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatFriendAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getRId() {
        return R.layout.recycle_view;
    }

//    @Override
//    protected View getTitleBarView() {
//        return null;
//    }


    @Override
    public void left() {

    }


    @Override
    public void right() {
        count += 1;
//        String uid, String name, String header, String nickname,
//                String gid, int type
        ChatFriendsImpl.getInstance(getContext()).add(new ChatFriendBean(count + "", "群组+" + count, TestConstant.IMAGE, "群组+" + count, "", 2));
        datas = ChatFriendsImpl.getInstance(getContext()).getDatas();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void center() {

    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setOnTitleBarListener(this).setRightContent("添加");
    }


    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }
}
