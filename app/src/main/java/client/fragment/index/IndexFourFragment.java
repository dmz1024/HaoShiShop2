package client.fragment.index;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import client.R;
import client.fragment.zongqinghui.IndexFourFlockFragment;
import client.fragment.zongqinghui.IndexFourFriendFragment;
import client.view.FriendAndFlockTitleBarView;
import view.NoScrollViewPager;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class IndexFourFragment extends NotNetWorkBaseFragment implements FriendAndFlockTitleBarView.OnFriendAndFlockTitleBarListener {
    @BindView(R.id.vp_content)
    NoScrollViewPager vp_content;

    @Override
    protected void initData() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new IndexFourFlockFragment());
        fragments.add(new IndexFourFriendFragment());
        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });


    }

    @Override
    protected int getRId() {
        return R.layout.no_scroll_view_pager;
    }

    @Override
    protected void initTitleView() {
        ((FriendAndFlockTitleBarView) getTitleBar())
                .showVisiLeft(View.GONE)
                .setOnTitleBarListener(this);
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }


    @Override
    protected View getTitleBarView() {
        return new FriendAndFlockTitleBarView(getContext());
    }

    @Override
    public void left() {

    }

    @Override
    public void flock() {
        vp_content.setCurrentItem(0,false);
    }

    @Override
    public void friend() {
        vp_content.setCurrentItem(1,false);
    }

    @Override
    public void right() {

    }
}
