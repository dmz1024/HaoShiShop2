package haoshi.com.shop.fragment.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.bean.ChooseStringBean;
import base.bean.SingleBaseBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.ChatFriendGroupAdapter;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendGroupBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendGroupImpl;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.FriendAndFlockController;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;
import view.pop.PopEdit;
import view.pop.TipMessage;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ChatFriendFragment extends ListNetWorkBaseFragment<ChatFriendGroupNetBean> implements OnTitleBarListener {

    public static ChatFriendFragment getInstance(boolean sendShape) {
        ChatFriendFragment fragment = new ChatFriendFragment();
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

    @Override
    protected String url() {
        return ApiConstant.FRIENDS_LIST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    private boolean isFirstNative = true;

    @Override
    protected void initData() {
        if (!sendShape) {
            initAddGroupRxBus();
            if (isFirstNative) {
                isFirstNative = false;
                nativeData();
            }
        }

        super.initData();
    }


    @Override
    protected Class<ChatFriendGroupNetBean> getTClass() {
        return ChatFriendGroupNetBean.class;
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return sendShape ? super.getDefaultView() : ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }


    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ChatFriendGroupAdapter(getContext(), (ArrayList<ChatFriendGroupBean>) totalList, sendShape) {
            @Override
            protected void edit(final int layoutPosition) {

                ArrayList<ChooseStringBean> datas = new ArrayList<>();
                datas.add(new ChooseStringBean("编辑分组名称"));
                datas.add(new ChooseStringBean("删除分组"));
                new ChooseStringView<ChooseStringBean>(getContext(), datas) {
                    @Override
                    protected void itemClick(final int position) {
                        switch (position) {
                            case 0:
                                new PopEdit(getContext(), list.get(layoutPosition).getName()) {
                                    @Override
                                    protected void content(final String content) {
                                        FriendAndFlockController.getInstance().updateGroupName(list.get(layoutPosition).getID(), content, new OnSingleRequestListener<SingleBaseBean>() {
                                            @Override
                                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                                list.get(layoutPosition).setName(content);
                                                notifyItemChanged(layoutPosition);
                                            }

                                            @Override
                                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                                            }
                                        });
                                    }
                                }.showAtLocation(false);
                                break;
                            case 1:
                                new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "是否确认删除分组？\n删除后好友将移至我的好友分组", "取消", "删除")) {
                                    @Override
                                    protected void right() {
                                        super.right();
                                        FriendAndFlockController.getInstance().deleteGroup(list.get(layoutPosition).getID(), new OnSingleRequestListener<SingleBaseBean>() {
                                            @Override
                                            public void succes(boolean isWrite, SingleBaseBean bean) {

//                                                ArrayList<ChatFriendBean> friends = list.get(layoutPosition).getFriends();
//                                                String id = list.get(layoutPosition).getID();
//                                                for (ChatFriendBean friend : friends) {
//                                                    friend.setGid(id);
//                                                }
//
//                                                ChatFriendsImpl.getInstance().addAll(friends);
//                                                ChatFriendGroupImpl.getInstance().delete(list.get(layoutPosition));
//                                                totalList.clear();
//                                                ((ArrayList<ChatFriendGroupBean>) totalList).addAll(ChatFriendGroupImpl.getInstance().getDatas());
//                                                notifyDataSetChanged();
                                                getData();
                                            }

                                            @Override
                                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                                            }
                                        });
                                    }
                                }.showAtLocation(false);
                                break;
                        }
                    }
                }.showAtLocation(false);
            }
        };
    }

    private void nativeData() {
        totalList.addAll(ChatFriendGroupImpl.getInstance().getDatas());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isCanRefresh() {
        return !sendShape;
    }

    @Override
    protected View getTitleBarView() {
        return sendShape ? super.getTitleBarView() : null;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setOnTitleBarListener(this)
                .setTitleContent("站内好友");
    }

    @Override
    protected boolean isCanFirstInitData() {
        return sendShape;
    }


    private Observable<String> groupNotifyDataRxBus;

    private void initAddGroupRxBus() {
        if (groupNotifyDataRxBus == null) {
            groupNotifyDataRxBus = RxBus.get().register("groupNotifyDataRxBus", String.class);
            groupNotifyDataRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    if (mAdapter != null) {
                        getData();
                    }
                }
            });
        }

    }

    @Override
    protected int time() {
        return sendShape ? 500 : super.time();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("groupNotifyDataRxBus", groupNotifyDataRxBus);
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
    protected boolean getLoadMore() {
        return false;
    }
}
