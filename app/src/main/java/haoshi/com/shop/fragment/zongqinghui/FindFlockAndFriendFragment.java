package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.zongqinghui.FindFlockBean;
import haoshi.com.shop.view.FriendAndFlockTitleBarView;
import util.GlideUtil;
import util.RxBus;
import view.NoScrollViewPager;


/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFlockAndFriendFragment extends NotNetWorkBaseFragment implements FriendAndFlockTitleBarView.OnFriendAndFlockTitleBarListener {

    public static FindFlockAndFriendFragment getInstance(int position) {
        FindFlockAndFriendFragment fragment = new FindFlockAndFriendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }
    }

    private int position;

    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_nick_name)
    TextView tv_nick_name;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.rl_info)
    RelativeLayout rl_info;
    @BindView(R.id.vp_content)
    NoScrollViewPager vp_content;

    private OnUserInfoInterface userInfo = new OnUserInfoInterface() {
        @Override
        public void user(FindFlockBean.User user) {
            GlideUtil.GlideErrAndOc(getContext(), user.userPhoto, iv_head);
            tv_nick_name.setText(user.name);
            tv_info.setText(user.property);
        }
    };


    @Override
    protected View getTitleBarView() {
        return new FriendAndFlockTitleBarView(getContext());
    }


    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void flock() {
        vp_content.setCurrentItem(0, false);
    }

    @Override
    public void friend() {
        vp_content.setCurrentItem(1, false);
    }

    @Override
    public void right() {

    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initData() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        FindFlockFragment flockFragment = FindFlockFragment.getInstance(position == 0);
        flockFragment.setOnUserInfoInterface(userInfo);
        FindFriendFragment friendFragment = FindFriendFragment.getInstance(position == 1);
        friendFragment.setOnUserInfoInterface(userInfo);
        fragments.add(flockFragment);
        fragments.add(friendFragment);

        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void finishUpdate(ViewGroup container) {
                try {
                    super.finishUpdate(container);
                } catch (NullPointerException e) {
                    Log.d("finishUpdate", e.getMessage() + "--");
                }
            }
        });

        vp_content.setCurrentItem(position, false);
        ((FriendAndFlockTitleBarView) getTitleBar()).changeColor(position + 1);
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_find_flock_friend;
    }

    @Override
    protected void initTitleView() {
        ((FriendAndFlockTitleBarView) getTitleBar()).setOnTitleBarListener(this);
    }
}
