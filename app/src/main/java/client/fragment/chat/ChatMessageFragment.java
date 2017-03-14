package client.fragment.chat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import client.R;
import client.adapter.ChatMessageAdapter;
import client.bean.chat.impl.ChatFriendsImpl;
import client.bean.chat.impl.ChatMessagesImpl;
import client.bean.chat.dao.ChatMessageBean;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ChatMessageFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private ArrayList<ChatMessageBean> datas;
    ChatMessageAdapter mAdapter;


    @Override
    protected void initData() {
        datas = ChatMessagesImpl.getInstance(getContext().getApplicationContext()).getDatas();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ChatMessageAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
        initNotifyRxBus();
    }

    @Override
    protected int getRId() {
        return R.layout.recycle_view;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    private Observable<String> messageRxBus;

    private void initNotifyRxBus() {
        if (messageRxBus == null) {
            messageRxBus = RxBus.get().register("message", String.class);
            messageRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    datas = ChatMessagesImpl.getInstance(getContext()).getDatas();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message", messageRxBus);
    }
}
