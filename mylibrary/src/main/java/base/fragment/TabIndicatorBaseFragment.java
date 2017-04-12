package base.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2016/12/7.
 */

public abstract class TabIndicatorBaseFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    public TabLayout tab;
    ViewPager vp_content;
    ArrayList<Fragment> fragments;
    String[] tabTitles;

    @Override
    protected void initData() {
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
                return tabTitles[position];
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
        vp_content.setCurrentItem(getCurrent());
    }



    protected int getCurrent() {
        return 0;
    }


    @Override
    protected void initView() {
        tab= (TabLayout) rootView.findViewById(R.id.tablayout);
        vp_content= (ViewPager) rootView.findViewById(R.id.vp_content);
        fragments = getFragments();
        tabTitles = getTabTitles();
        vp_content.setOffscreenPageLimit(fragments.size());


    }

    protected abstract ArrayList<Fragment> getFragments();

    protected abstract String[] getTabTitles();

    protected abstract String getTitleContent();

    @Override
    protected int getRId() {
        return R.layout.fragment_base_indicator;
    }

    @Override
    protected View getTitleBarView() {
        return new DefaultTitleBarView(getContext());
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent(getTitleContent()).setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back","back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
