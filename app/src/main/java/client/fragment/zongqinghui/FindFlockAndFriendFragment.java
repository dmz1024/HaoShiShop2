package client.fragment.zongqinghui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.NotNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import client.CeshiUrl;
import client.R;
import client.bean.zongqinghui.FindUserInfoBean;
import client.view.FriendAndFlockTitleBarView;
import util.RxBus;
import view.NoScrollViewPager;


/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFlockAndFriendFragment extends SingleNetWorkBaseFragment<FindUserInfoBean> implements FriendAndFlockTitleBarView.OnFriendAndFlockTitleBarListener {
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

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "single");
        return super.map();
    }

    @Override
    protected Class<FindUserInfoBean> getTClass() {
        return FindUserInfoBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_find_flock_friend, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void writeData(boolean isWrite, FindUserInfoBean bean) {
        super.writeData(isWrite, bean);
        ((FriendAndFlockTitleBarView) getTitleBar()).setOnTitleBarListener(this);

        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FindFlockFragment());
        fragments.add(new FindFriendFragment());

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
    }

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
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
