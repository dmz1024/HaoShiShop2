package haoshi.com.shop.fragment.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChatFriendAdapter;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ChatFlockFragment extends SingleNetWorkBaseFragment<ChatFlockNetBean> implements OnTitleBarListener {

    public static ChatFlockFragment getInstance(boolean sendShape) {
        ChatFlockFragment fragment = new ChatFlockFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("sendShape", sendShape);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean sendShape;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            sendShape = bundle.getBoolean("sendShape");
        }
    }

    private ArrayList<ChatFriendBean> datas = new ArrayList<>();

    private boolean isFirstNative = true;
    private ChatFriendAdapter adapter;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    private void nativeData() {
        datas.addAll(ChatFriendsImpl.getInstance().setType(1).getDatas());
        adapter = new ChatFriendAdapter(getContext(), datas, sendShape);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(adapter);
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return sendShape ? super.getDefaultView() : ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected void writeData(boolean isWrite, ChatFlockNetBean bean) {
        super.writeData(isWrite, bean);
        bean.getData();
        datas.clear();
        datas.addAll(ChatFriendsImpl.getInstance().setType(1).getDatas());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String url() {
        return ApiConstant.JOIN_LIST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<ChatFlockNetBean> getTClass() {
        return ChatFlockNetBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.recycle_view, null);
        ButterKnife.bind(this, view);
        if (!sendShape) {
            initAddFlockRxBus();
            if (isFirstNative) {
                isFirstNative = false;
                nativeData();
            }
        } else {
            adapter = new ChatFriendAdapter(getContext(), datas, sendShape);
            rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_content.setAdapter(adapter);
        }

        return view;
    }

    @Override
    protected int time() {
        return sendShape ? 500 : 0;
    }

    @Override
    protected View getTitleBarView() {
        return sendShape ? super.getTitleBarView() : null;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setOnTitleBarListener(this)
                .setTitleContent("站内群组");
    }

    @Override
    protected boolean isCanFirstInitData() {
        return sendShape;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    private void initAddFlockRxBus() {

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
