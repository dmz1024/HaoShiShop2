package haoshi.com.shop.fragment.shop;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;

import base.adapter.MyLoopPagerAdapter;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.adapter.ShopIndexGoodsAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.bean.ShopIndexBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.fragment.my.AddressContentFragment;
import haoshi.com.haoshi.com.shop.view.ShopIndexView;
import interfaces.OnShowListDataListener;
import interfaces.OnTitleBarListener;
import interfaces.ScrollChangeViewListener;
import util.RxBus;
import view.DefaultTitleBarView;
import view.ScrollChangeScrollView;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class IndexTwoFragment extends ListNetWorkBaseFragment<ShopIndexBean> implements ScrollChangeViewListener, OnTitleBarListener {
    private ScrollChangeScrollView sc;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ShopIndexGoodsAdapter(getContext(), (ArrayList<ShopIndexBean.Data>) totalList);
    }
//1603

    @Override
    protected int getSize() {
        return 5;
    }

    @Override
    protected String url() {
        return ApiConstant.SHOP_INDEX;
    }

    @Override
    protected Class<ShopIndexBean> getTClass() {
        return ShopIndexBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("商城")
                .showVisiLeft(View.GONE).setRightContent("去地址").setOnTitleBarListener(this);
    }



    @Override
    protected OnShowListDataListener showListView() {
        return new ShopIndexView(getContext());
    }

    private LinearLayout ll;

    @Override
    protected void writeData(boolean isWrite, ShopIndexBean bean) {
        super.writeData(isWrite, bean);
        if (page == 1 && bean.result == 0 && havaDataView != null && bean.info != null) {
            ShopIndexView view = (ShopIndexView) havaDataView;
            (sc = view.getThis()).setScrollViewListener(this);
            ll = view.getLL();
            RollPagerView rv_ad = view.getAd();
            RollPagerView rv_activity = view.getActivi();
            RecyclerView rv_menu = view.getMenu();
            initAd(rv_ad, bean.info.ads);
            initActivity(rv_activity);
            initMenu(rv_menu);
        }
    }

    @Override
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    /**
     * 快捷菜单
     *
     * @param rv_menu
     */
    private void initMenu(RecyclerView rv_menu) {
        ArrayList<GeneralBean> datas = new ArrayList<>();
//        String title, int rid, Fragment fragment, int type
        datas.add(new GeneralBean("团购活动", R.mipmap.shangcheng_rementuangou, new GoodsIndexRootFragment(), 12));
        datas.add(new GeneralBean("商家精选", R.mipmap.shangcheng_jingxuan, new MerchantSiftFragment(), 12));
        datas.add(new GeneralBean("购物车", R.mipmap.gouwuche, new BuyCarRootFragment(), 12));
        datas.add(new GeneralBean("全部分类", R.mipmap.shangcheng_fenlei, new GoodAllClassifyFragment(), 12));
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas);
        rv_menu.setLayoutManager(manager);
        rv_menu.setAdapter(mAdapter);
    }

    /**
     * 限时活动
     *
     * @param rv_activity
     */
    private void initActivity(RollPagerView rv_activity) {
        rv_activity.setVisibility(View.GONE);
    }

    /**
     * 广告轮播图
     *
     * @param rv_ad
     * @param ads
     */
    private void initAd(RollPagerView rv_ad, ArrayList<ShopIndexBean.InfoBean.AdsBean> ads) {
        if (ads != null && ads.size() > 0) {
            rv_ad.setPlayDelay(ads.size() > 1 ? 4000 : 0);
            rv_ad.setHintView(new ColorPointHintView(getContext(), Color.parseColor("#ee9821"), Color.WHITE));
            rv_ad.setAdapter(new MyLoopPagerAdapter(rv_ad, ads));
            rv_ad.getViewPager().setPageTransformer(true, new AccordionTransformer());
        }

    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected boolean getLoadMore() {
        return true;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < oldy ||y < (ll.getHeight() - sc.getHeight() - 500)) {
            return;
        }
        moreData();
    }

    @Override
    protected boolean getLoadMoreCanShowTipLoading() {
        return true;
    }

    @Override
    protected TipLoadingBean getTipLoadingBeanForListNet() {
        return page==1?null:new TipLoadingBean("加载更多","","");
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {
        RxBus.get().post("addFragment",new AddFragmentBean(new AddressContentFragment()));
    }

    @Override
    public void center() {

    }
}
