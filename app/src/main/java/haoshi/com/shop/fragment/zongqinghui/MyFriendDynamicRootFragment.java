package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import base.fragment.ListNetWorkBaseFragment;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.zongqinghui.FriendDynamicBean;
import interfaces.OnTitleBarListener;
import util.GlideUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class MyFriendDynamicRootFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.fg_content)
    FrameLayout fg_content;

    @OnClick(R.id.tv_chat)
    void chat() {

    }

    public static MyFriendDynamicRootFragment getInstance(String id) {
        MyFriendDynamicRootFragment fragment = new MyFriendDynamicRootFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
    }

    private String id;


    private MyFriendDynamicFragment fragment;


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("亲友动态")
                .setOnTitleBarListener(this);
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
    protected String getBackColor() {
        return "#f6f6f6";
    }


    @Override
    protected void initData() {
        fragment = MyFriendDynamicFragment.getInstance(id);
        fragment.setDyInfo(new MyFriendDynamicFragment.MyFriendDyInfo() {
            @Override
            public void info(FriendDynamicBean.Info info) {
                rl_top.setVisibility(View.VISIBLE);
                GlideUtil.GlideErrAndOc(getContext(), info.userPhoto, iv_bg);
                GlideUtil.GlideErrAndOc(getContext(), info.userPhoto, iv_head);
                tv_name.setText(info.userName);

            }
        });
        fragment.setOnScrollListener(new ListNetWorkBaseFragment.OnListBaseScrollListener() {
            @Override
            public void toDown(int dy, boolean firstShow) {
                Log.d("滑动", "toDown"+firstShow);

            }

            @Override
            public void toTop(int dy, boolean firstShow) {
                Log.d("滑动", "toTop"+firstShow);
            }
        });
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, fragment).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_my_friend_dynamic;
    }


    private void toDown() {

    }
}
