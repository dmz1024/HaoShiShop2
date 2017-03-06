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

    private int fCount = 0;

    @Override
    protected void initData() {
        datas = ChatFriendGroupImpl.getInstance(getContext()).getDatas();
        count = datas.size();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatFriendGroupAdapter(getContext(), datas) {
            @Override
            protected void edit(int layoutPosition) {
                fCount = 0;
                for (ChatFriendGroupBean f : datas) {
                    fCount += f.getCount();
                }
                ChatFriendsImpl.getInstance(getContext()).add(new ChatFriendBean(fCount + "", "好友+" + fCount, TestConstant.IMAGE, "好友+" + fCount, datas.get(layoutPosition).getGid(), 1));
                datas = ChatFriendGroupImpl.getInstance(getContext()).getDatas();
                notifyDataSetChanged();
            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    int count = 0;

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
        count = count + 1;
        ChatFriendGroupImpl.getInstance(getContext()).add(new ChatFriendGroupBean(count + "", "1", "分组+" + count));
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
