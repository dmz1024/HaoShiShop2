package haoshi.com.shop.fragment.discover;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.DiscoverTabBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/15.
 */

public class DiscoverCollectRootFragment extends SingleNetWorkBaseFragment<DiscoverTabBean> implements OnTitleBarListener {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_content)
    ViewPager vp_content;
    @BindView(R.id.iv_send)
    ImageView iv_send;

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
        View view = View.inflate(getContext(), R.layout.fragment_index_two, null);
        ButterKnife.bind(this, view);
        iv_send.setVisibility(View.GONE);
        return view;
    }

    private ArrayList<DiscoverCollectFragment> fragments;
    private ArrayList<Boolean> deleteDatas;

    @Override
    protected void writeData(boolean isWrite, final DiscoverTabBean bean) {
        super.writeData(isWrite, bean);
        fragments = new ArrayList<>();
        deleteDatas = new ArrayList<>();
        for (int i = 0; i < bean.data.size(); i++) {
            fragments.add(DiscoverCollectFragment.getInstance(bean.data.get(i).catId, i == 0));
            deleteDatas.add(false);
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

            @Override
            public void finishUpdate(ViewGroup container) {
                try {
                    super.finishUpdate(container);
                } catch (NullPointerException e) {
                    Log.d("finishUpdate", e.getMessage() + "--");
                }
            }
        });

        tab.setupWithViewPager(vp_content);
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("发现")
                .setRightContent("编辑")
                .setOnTitleBarListener(this);
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        if (vp_content != null && fragments != null && fragments.size() > 0) {
            deleteDatas.set(vp_content.getCurrentItem(), !deleteDatas.get(vp_content.getCurrentItem()));
            fragments.get(vp_content.getCurrentItem()).showDelete(deleteDatas.get(vp_content.getCurrentItem()));
        }
    }

    @Override
    public void center() {

    }
}
