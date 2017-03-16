package client.fragment.discover;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import client.CeshiUrl;
import client.R;
import client.bean.DiscoverTabBean;
import client.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/15.
 */

public class DiscoverCollectRootFragment extends SingleNetWorkBaseFragment<DiscoverTabBean> implements OnTitleBarListener {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_content)
    ViewPager vp_content;

    @Override
    protected String url() {
        return ApiConstant.ONEARTICLE;
    }


    @Override
    protected Class<DiscoverTabBean> getTClass() {
        return DiscoverTabBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_two_index, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void writeData(boolean isWrite, final DiscoverTabBean bean) {
        super.writeData(isWrite, bean);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < bean.data.size(); i++) {
            fragments.add(DiscoverFragment.getInstance(bean.data.get(i).catId,i==0));
        }
        vp_content.setOffscreenPageLimit(fragments.size());
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
            public CharSequence getPageTitle(int position) {
                return bean.data.get(position).catName;
            }
        });

        tab.setupWithViewPager(vp_content);
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("发现")
                .setLeftImage(R.mipmap.leimu2)
                .setRightContent("编辑").setOnTitleBarListener(this);
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
