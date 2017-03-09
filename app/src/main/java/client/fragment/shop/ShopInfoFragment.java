package client.fragment.shop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.NetworkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import client.CeshiUrl;
import client.R;
import client.bean.shop.ShopInfoBean;
import util.Util;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/9.
 */

public class ShopInfoFragment extends SingleNetWorkBaseFragment<ShopInfoBean> {
    @BindView(R.id.fg_view)
    FrameLayout fg_view;
    @BindView(R.id.vp_content)
    ViewPager vp_content;

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
    protected Class<ShopInfoBean> getTClass() {
        return ShopInfoBean.class;
    }


    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_shop, null);
        ButterKnife.bind(this, view);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) fg_view.getLayoutParams();
        params.width = Util.getWidth() / 3;
        fg_view.setLayoutParams(params);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(GoodsIndexRootFragment.getInstance(null,1));
        fragments.add(GoodsIndexRootFragment.getInstance(null,1));
        fragments.add(new GoodEvaluateFragment());
        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        return view;
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("七匹狼旗舰店");
    }
}
