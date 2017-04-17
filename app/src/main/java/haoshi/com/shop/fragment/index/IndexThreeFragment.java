package haoshi.com.shop.fragment.index;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.AllDiscoverClassifyBean;
import haoshi.com.shop.bean.discover.DiscoverTabBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.fragment.discover.AllDiscoverClassifyFragment;
import haoshi.com.shop.fragment.discover.DiscoverFragment;
import haoshi.com.shop.fragment.my.MyGiveFragment;
import haoshi.com.shop.pop.PopSendDiscover;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class IndexThreeFragment extends SingleNetWorkBaseFragment<AllDiscoverClassifyBean> implements OnTitleBarListener {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_content)
    ViewPager vp_content;

    @Override
    protected String url() {
        return ApiConstant.GETTREES;
    }
//    ApiConstant.GETTREES

    @Override
    protected Class<AllDiscoverClassifyBean> getTClass() {
        return AllDiscoverClassifyBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_index_two, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private AllDiscoverClassifyBean bean;

    @Override
    protected void writeData(boolean isWrite, final AllDiscoverClassifyBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < bean.data.size(); i++) {
            fragments.add(DiscoverFragment.getInstance(bean.data.get(i).catId, i == 0));
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
                .setOnTitleBarListener(this);
//        .setRightImage(R.mipmap.seach2)
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    public void left() {
        RxBus.get().post("addFragment",new AddFragmentBean(new AllDiscoverClassifyFragment()));
    }

    @Override
    public void right() {
        RxBus.get().post("addFragment",new AddFragmentBean(new MyGiveFragment()));
    }

    @Override
    public void center() {

    }
}
