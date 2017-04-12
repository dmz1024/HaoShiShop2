package haoshi.com.shop.fragment.shop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.bean.SerializableMap;
import base.bean.TipLoadingBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.GoodAllClassifyBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.pop.PopGoodsFilter;
import interfaces.OnTitleBarListener;
import util.DrawableUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/9.
 */

public class GoodsIndexRootFragment extends SingleNetWorkBaseFragment<GoodAllClassifyBean> implements OnTitleBarListener {
    @BindViews({R.id.tv_filter, R.id.tv_price, R.id.tv_count})
    List<TextView> tvs;

    private GoodAllClassifyBean allBean;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    private GoodGroupActivityFragment fragment;
    private SerializableMap serializableMap;

    public static GoodsIndexRootFragment getInstance(SerializableMap Sfilter) {
        GoodsIndexRootFragment fragment = new GoodsIndexRootFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Sfilter", Sfilter);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            serializableMap = (SerializableMap) bundle.getSerializable("Sfilter");
        }

        if (serializableMap == null) {
            serializableMap = new SerializableMap();
            serializableMap.setMap(new HashMap<String, String>());
        }
    }


    @Override
    protected void initTitleView() {
        String title = "团购活动";
        catName = serializableMap.getMap().get("catName");
        keyWord = serializableMap.getMap().get("keyword");
        if (TextUtils.isEmpty(catName)) {
            if (!TextUtils.isEmpty(keyWord)) {
                title = keyWord;
            }
        } else {
            title = catName;
            catId = serializableMap.getMap().get("catsId");
        }

        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent(title).setOnTitleBarListener(this);
    }

    @Override
    protected String url() {
        return ApiConstant.GOOD_ALL_CLASSIFY;
    }

    @Override
    protected Class<GoodAllClassifyBean> getTClass() {
        return GoodAllClassifyBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_goods_index_root, null);
        ButterKnife.bind(this, view);
        fragment = GoodGroupActivityFragment.getInstance(serializableMap);
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, fragment).commit();
        return view;
    }

    private String catId;
    private String catName;
    private String keyWord;

    private void setFilter() {
        if (fragment != null) {
            Map<String, String> filter = new HashMap<>();
            if (!TextUtils.isEmpty(catId)) {
                filter.put("catsId", catId);
            }
            if (!TextUtils.isEmpty(keyWord)) {
                filter.put("keyWord", keyWord);
            }
            if (count == 2) {
                filter.put("saleNumSort", "desc");
            }
            if (price != 0) {
                filter.put("shopPriceSort", price == 1 ? "asc" : "desc");
            }
            fragment.setFileter(filter);
        }
    }

    @Override
    protected void writeData(boolean isWrite, GoodAllClassifyBean bean) {
        super.writeData(isWrite, bean);
        allBean = bean;
        showFilter();
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @OnClick({R.id.fg_filter, R.id.fg_price, R.id.fg_count})
    void tvOnClick(View view) {
        switch (view.getId()) {
            case R.id.fg_filter:
                filter();
                break;
            case R.id.fg_price:
                price();
                break;
            case R.id.fg_count:
                count();
                break;
        }
    }


    private int price = 0;
    private int count = 0;

    /**
     * 销量排序
     */
    private void count() {
        price = 0;
        count = count == 0 ? 2 : 0;
        changeIcon();
    }

    /**
     * 价格排序
     */
    private void price() {
        count = 0;
        price = price == 0 ? 1 : (price == 2 ? 0 : 2);
        changeIcon();
    }

    /**
     * 分类
     */
    private void filter() {
        if (allBean == null) {
            getData();
        } else {
            showFilter();
        }
    }


    private void changeIcon() {
        tvs.get(1).setTextColor(Color.parseColor(price == 0 ? "#333333" : "#ee9821"));
        tvs.get(1).setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(price == 0 ? R.mipmap.shangcheng_iconfont_icon09 : (price == 1 ? R.mipmap.shangcheng_iconfont_icon10 : R.mipmap.shangcheng_iconfont_icon11))), null);
        tvs.get(2).setTextColor(Color.parseColor(count == 0 ? "#333333" : "#ee9821"));
        tvs.get(2).setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(count == 0 ? R.mipmap.shangcheng_iconfont_icon08 : R.mipmap.shangcheng_iconfont_icon07)), null);
        setFilter();
    }


    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("获取分类", "", "");
    }


    private void showFilter() {
        new PopGoodsFilter(getContext(), allBean) {
            @Override
            protected void chooseFilter(int fatherPosition, int childerPosition) {
                super.chooseFilter(fatherPosition, childerPosition);
                GoodAllClassifyBean.Data data = allBean.data.get(fatherPosition);
                if (childerPosition != -1) {
                    final GoodAllClassifyBean.Data.CatBean catBean = data.list.get(childerPosition);
                    catId = catBean.catId;
                    catName = catBean.catName;
                } else {
                    catId = data.catId;
                    catName = data.catName;
                }
                ((DefaultTitleBarView) getTitleBar()).setTitleContent(catName);

                setFilter();
            }
        }.showAsDropDown(ll_top, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allBean = null;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return true;
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
