package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Map;

import base.bean.ChooseStringBean;
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.MessageBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;
import haoshi.com.shop.bean.zongqinghui.FriendInfoBean;
import haoshi.com.shop.bean.zongqinghui.SubGroupBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.FriendAndFlockController;
import haoshi.com.shop.fragment.chat.ChatViewFragment;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;
import view.pop.PopEdit;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class FriendInfoFragment extends SingleNetWorkBaseFragment<FriendInfoBean> implements OnTitleBarListener {
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_nick_name)
    TextView tv_nick_name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_group)
    TextView tv_group;
    @BindView(R.id.tv_beizhu)
    TextView tv_beizhu;
    @BindView(R.id.bt_send)
    Button bt_send;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.rl_bei)
    RelativeLayout rl_bei;

    public static FriendInfoFragment getInstance(String fid, int type) {
        FriendInfoFragment fragment = new FriendInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String fid;
    private int type;


    @Override
    protected void writeData(boolean isWrite, FriendInfoBean bean) {
        super.writeData(isWrite, bean);
        FriendInfoBean.Data data = bean.data;
        Glide.with(getContext()).load(data.userPhoto).into(iv_head);
        tv_nick_name.setText(data.userName);
        tv_beizhu.setText(data.note);
        tv_group.setText(data.groupname);
        tv_name.setText(data.loginName);
        if (TextUtils.equals(fid, UserInfo.userId)) {
            bt_delete.setVisibility(View.GONE);
            bt_send.setVisibility(View.GONE);
            rl_bei.setVisibility(View.GONE);
        } else if(bean.data.isadd==1){
            ((DefaultTitleBarView) getTitleBar()).setRightImage(R.mipmap.zqh_more);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fid = bundle.getString("fid");
            type = bundle.getInt("type");
        }
    }

    @Override
    protected String url() {
        return ApiConstant.FRIEND_INFO;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("fid", fid);
        return super.map();
    }

    @Override
    protected Class<FriendInfoBean> getTClass() {
        return FriendInfoBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_friend_info, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @OnClick(R.id.bt_dongtai)
    void dongtai() {
        RxBus.get().post("addFragment", new AddFragmentBean(MyFriendDynamicRootFragment.getInstance(fid, type)));
    }

    @OnClick(R.id.bt_send)
    void send() {
        RxBus.get().post("back", "back");
        if (type == 1) {
            RxBus.get().post("back", "back");
            RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(fid)));
        }
    }

    @OnClick(R.id.tv_beizhu)
    void beizhu() {
        new PopEdit(getContext(), tv_beizhu.getText().toString()) {
            @Override
            protected void content(final String content) {
                super.content(content);
                FriendAndFlockController.getInstance().editNote(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        tv_beizhu.setText(content);
                        RxBus.get().post("groupNotifyDataRxBus", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                    }
                }, fid, content);
            }
        }.showAtLocation(false);
    }

    private ArrayList<SubGroupBean.Data> groups;

    @OnClick(R.id.tv_group)
    void group() {
        if (groups == null) {
            FriendAndFlockController.getInstance().getGroupList(new OnSingleRequestListener<SubGroupBean>() {
                @Override
                public void succes(boolean isWrite, SubGroupBean bean) {
                    groups = bean.getData();
                    showGroup();
                }

                @Override
                public void error(boolean isWrite, SubGroupBean bean, String msg) {

                }
            });
        } else {
            showGroup();
        }
    }

    private void showGroup() {
        if (groups.size() == 0) {
            MyToast.showToast("没有其它分组");
            return;
        }


        new ChooseStringView<SubGroupBean.Data>(getContext(), groups) {
            @Override
            protected void itemClick(final int position) {
                super.itemClick(position);
                FriendAndFlockController.getInstance().editSubGroup(new OnSingleRequestListener<SingleBaseBean>() {
                    @Override
                    public void succes(boolean isWrite, SingleBaseBean bean) {
                        tv_group.setText(groups.get(position).name);
                        RxBus.get().post("groupNotifyDataRxBus", "");
                    }

                    @Override
                    public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                    }
                }, fid, groups.get(position).id);
            }
        }.showAtLocation(false);
    }

    @OnClick(R.id.bt_delete)
    void delete() {
        FriendAndFlockController.getInstance().deleteFriend(fid, new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                ChatFriendsImpl.getInstance().delete(fid);
                MessageBean select = MessagesImpl.getInstance().select(fid);
                if (select != null) {
                    MessagesImpl.getInstance().delete(select);
                    RxBus.get().post("message", "");
                }
                RxBus.get().post("groupNotifyDataRxBus", "");
                RxBus.get().post("back", "back");
                RxBus.get().post("back", "back");
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        });
    }


    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        ArrayList<ChooseStringBean> choose = new ArrayList<>();
        choose.add(new ChooseStringBean("删除好友"));
        new ChooseStringView<ChooseStringBean>(getContext(), choose) {
            @Override
            protected void itemClick(int position) {
                super.itemClick(position);
                delete();
            }
        }.showAtLocation(false);
    }

    @Override
    public void center() {

    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("用户信息").setOnTitleBarListener(this);

    }
}
