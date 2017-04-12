package haoshi.com.shop.fragment.shop;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.bean.SerializableMap;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.shop.ShopInfoBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.controller.ShareUtil;
import haoshi.com.shop.fragment.BaseShapeFragment;
import interfaces.OnTitleBarListener;
import util.RxBus;
import util.Util;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/9.
 */

public class ShopInfoFragment extends BaseShapeFragment<ShopInfoBean> implements OnTitleBarListener {
    @BindView(R.id.fg_view)
    FrameLayout fg_view;
    @BindView(R.id.vp_content)
    ViewPager vp_content;
    @BindViews({R.id.tv_activity, R.id.tv_all_good, R.id.tv_evaluate})
    List<TextView> tvs;

    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;
    private String shopId;

    public static ShopInfoFragment getInstance(String shopId) {
        ShopInfoFragment fragment = new ShopInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("shopId", shopId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            shopId = bundle.getString("shopId");
        }
    }

    @Override
    protected String url() {
        return ApiConstant.SHOP_INFO;
    }

    @Override
    protected Map<String, String> map() {
        map.put("shopId", shopId);
        return super.map();
    }

    @Override
    protected Class<ShopInfoBean> getTClass() {
        return ShopInfoBean.class;
    }

    private ShopInfoBean bean;

    @Override
    protected void writeData(boolean isWrite, ShopInfoBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        initVp();
        initShopInfo(bean.data);
    }

    @Override
    protected boolean getIsGood() {
        return false;
    }

    @Override
    protected SendBean getSendBean() {
        return null;
    }

    private void initShopInfo(ShopInfoBean.Data data) {
        Glide.with(getContext()).load(data.shopImg).into(iv_bg);
        Glide.with(getContext()).load(data.shopImg).into(iv_head);
        tv_name.setText(data.shopName);
        tv_content.setText(data.shopDesc);
        initType(data.accreds);
        ((DefaultTitleBarView) getTitleBar()).setTitleContent(data.shopName);
    }

    private void initType(List<ShopInfoBean.Data.AccredsBean> accreds) {
        ll_type.removeAllViews();
        for (int i = 0; accreds != null && i < accreds.size(); i++) {
            ImageView iv = new ImageView(getContext());
            ll_type.addView(iv);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Util.dp2Px(14), Util.dp2Px(14));
            Glide.with(getContext()).load(accreds.get(i).accredImg).into(iv);
            params.setMargins(2, 0, 0, 0);
            iv.setLayoutParams(params);

        }
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_shop, null);
        ButterKnife.bind(this, view);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) fg_view.getLayoutParams();
        params.width = Util.getWidth() / 3;
        fg_view.setLayoutParams(params);


        return view;
    }


    private void initVp() {
        final ArrayList<Fragment> fragments = new ArrayList<>();

        SerializableMap sm1 = new SerializableMap();
        Map<String, String> map1 = new HashMap<>();
        map1.put("shopId", shopId);
        sm1.setMap(map1);

        SerializableMap sm2 = new SerializableMap();
        Map<String, String> map2 = new HashMap<>();
        map2.put("shopId", shopId);
        sm2.setMap(map2);

        fragments.add(GoodGroupActivityFragment.getInstance(sm1, true, false));
        fragments.add(GoodGroupActivityFragment.getInstance(sm2, false, false));
        fragments.add(GoodEvaluateFragment.getInstance("shopId," + shopId, false));
        vp_content.setOffscreenPageLimit(3);
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
        vp_content.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeImageColor(position);
            }
        });


    }


    @OnClick({R.id.tv_activity, R.id.tv_all_good, R.id.tv_evaluate})
    void tvs(View view) {
        int position = 0;
        switch (view.getId()) {
            case R.id.tv_all_good:
                position = 1;
                break;
            case R.id.tv_evaluate:
                position = 2;
                break;
        }
        changeImageColor(position);
    }

    private int index = 0;

    private void changeImageColor(int position) {
        if (index == position) {
            return;
        }
        tvs.get(index).setTextColor(Color.parseColor("#333333"));
        tvs.get(position).setTextColor(Color.parseColor("#ee9821"));
        ObjectAnimator.ofFloat(fg_view, "translationX", tvs.get(index).getX(), tvs.get(position).getX()).setDuration(300).start();
        index = position;
        vp_content.setCurrentItem(index);
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setRightImage(R.mipmap.fenxiang).setOnTitleBarListener(this);
    }

    private boolean i;
    private RelativeLayout.LayoutParams params;


    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        if(isCanShape){
            showShare();
        }
    }

    @Override
    public void center() {

    }

    @Override
    protected ShareUtil.ShareInfo getShareInfo() {
        ShareUtil.ShareInfo shareInfo = new ShareUtil.ShareInfo();
        shareInfo.content = bean.data.shopDesc;
        shareInfo.title = bean.data.shopName;
        shareInfo.logo = bean.data.shopImg;
        shareInfo.url = "https://www.baidu.com";
        return shareInfo;
    }

    @Override
    protected boolean getShareShow() {

        return false;
    }
}
