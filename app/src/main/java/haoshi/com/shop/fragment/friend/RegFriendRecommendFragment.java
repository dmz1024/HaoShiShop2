package haoshi.com.shop.fragment.friend;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.FlockRecommendAdapter;
import haoshi.com.shop.adapter.FriendRecommendAdapter;
import haoshi.com.shop.bean.zongqinghui.FlockBean;
import haoshi.com.shop.bean.zongqinghui.FriendBean;
import haoshi.com.shop.bean.RegFriendRecommendBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.index.IndexFragment;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class RegFriendRecommendFragment extends SingleNetWorkBaseFragment<RegFriendRecommendBean> implements OnTitleBarListener {
    @BindView(R.id.rv_flocks)
    RecyclerView rv_flock;
    @BindView(R.id.rv_friend)
    RecyclerView rv_friend;


    @Override
    protected String url() {
        return ApiConstant.REG_COMMEND;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<RegFriendRecommendBean> getTClass() {
        return RegFriendRecommendBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view=View.inflate(getContext(), R.layout.fragment_reg_friend_recommend,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    protected void writeData(boolean isWrite, RegFriendRecommendBean bean) {
        super.writeData(isWrite, bean);
        initFriend(bean.data.users);
        initFlock(bean.data.group);
    }

    private void initFlock(ArrayList<FlockBean> flocks) {
        LinearLayoutManager manager=new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FlockRecommendAdapter mAdapter=new FlockRecommendAdapter(getContext(),flocks);
        rv_flock.setLayoutManager(manager);
        rv_flock.setAdapter(mAdapter);
    }

    private void initFriend(ArrayList<FriendBean> friends) {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FriendRecommendAdapter mAdapter=new FriendRecommendAdapter(getContext(),friends);
        rv_friend.setLayoutManager(manager);
        rv_friend.setAdapter(mAdapter);
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("推荐")
                .setRightContent("跳过")
                .setRightColor("#c2c2c2").showVisiLeft(View.GONE)
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        RxBus.get().post("back", "back");
        RxBus.get().post("addFragment", new AddFragmentBean(new IndexFragment()));

    }

    @Override
    public void center() {

    }

    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
