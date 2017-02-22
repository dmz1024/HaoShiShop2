package client.fragment.index;

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
import butterknife.OnClick;
import client.CeshiUrl;
import client.R;
import client.bean.DiscoverTabBean;
import client.fragment.discover.DiscoverFragment;
import client.pop.PopSendDiscover;
import interfaces.OnTitleBarListener;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class IndexTwoFragment extends SingleNetWorkBaseFragment<DiscoverTabBean> implements OnTitleBarListener {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_content)
    ViewPager vp_content;

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "discove");
        return super.map();
    }

    @Override
    protected Class<DiscoverTabBean> getTClass() {
        return DiscoverTabBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_index_two, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private DiscoverTabBean bean;

    @Override
    protected void writeData(boolean isWrite, final DiscoverTabBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < bean.data.size(); i++) {
            fragments.add(DiscoverFragment.getInstance(i == 0 ? "first" : "other"));
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
                return bean.data.get(position).title;
            }
        });

        tab.setupWithViewPager(vp_content);
    }


    @OnClick(R.id.iv_send)
    void send() {
        if (bean != null) {
            new PopSendDiscover(getContext(), bean.data).showAtLocation(false);
        }

    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("发现")
                .setLeftImage(R.mipmap.leimu2)
                .setRightImage(R.mipmap.seach2).setOnTitleBarListener(this);
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
