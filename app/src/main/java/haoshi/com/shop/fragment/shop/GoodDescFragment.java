package haoshi.com.shop.fragment.shop;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.Map;

import base.adapter.MyLoopPagerAdapter;
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import base.fragment.WebViewFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.AttrAdapter;
import haoshi.com.shop.adapter.GoodEvaluateAdapter;
import haoshi.com.shop.bean.AttrsBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.bean.shop.AddCarBean;
import haoshi.com.shop.bean.shop.GoodDescBean;
import haoshi.com.shop.bean.shop.GoodEvaluateBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.BuyCarController;
import haoshi.com.shop.controller.GoodController;
import haoshi.com.shop.controller.ShareUtil;
import haoshi.com.shop.fragment.BaseShapeFragment;
import haoshi.com.shop.fragment.chat.ChatViewFragment;
import haoshi.com.shop.pop.PopBuyCar;
import haoshi.com.shop.pop.PopDiscoverShare;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.DrawableUtil;
import util.MyToast;
import util.RxBus;
import util.TimeUtils;
import util.Util;
import view.DefaultTitleBarView;
import view.DragSwitchLayout;

/**
 * Created by dengmingzhi on 2017/3/7.
 */

public class GoodDescFragment extends BaseShapeFragment<GoodDescBean> implements OnTitleBarListener {
    private String good_id;
    @BindView(R.id.rpv_ad)
    RollPagerView rpv_ad;

    @BindView(R.id.tv_good_name)
    TextView tv_good_name;
    @BindView(R.id.tv_introduce)
    TextView tv_introduce;
    @BindView(R.id.tv_price_type)
    TextView tv_price_type;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_old_price)
    TextView tv_old_price;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rv_attr)
    RecyclerView rv_attr;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;

    @BindView(R.id.tv_all_evaluate)
    TextView tv_all_evaluate;
    @BindView(R.id.tv_collect)
    TextView tv_collect;
    @BindView(R.id.ds_root)
    DragSwitchLayout ds_root;

    public static GoodDescFragment getInstance(String good_id) {
        GoodDescFragment fragment = new GoodDescFragment();
        Bundle bundle = new Bundle();
        bundle.putString("good_id", good_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            good_id = bundle.getString("good_id");
//            good_id = "57";
        }
    }

    private String shopId;
    private String url;
    private GoodDescBean bean;

    @Override
    protected void writeData(boolean isWrite, GoodDescBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        ((DefaultTitleBarView) getTitleBar()).setOnTitleBarListener(this);
        shopId = bean.data.shopId;
        url = bean.data.url;
        initAd(bean.data.gallery);
        initGoodInfo(bean.data);
        initAttr(bean.data.attrs);
        initComment(bean.data.comment);
        initCollect(bean.data.favGood == 1);
        initDs();
    }

    @Override
    protected boolean getIsGood() {
        return true;
    }

    @Override
    protected SendBean getSendBean() {
        SendBean sendBean = new SendBean();
        sendBean.setName(bean.data.share.title);
        sendBean.setDesc(bean.data.share.info);
        sendBean.setId(good_id);
        sendBean.setImg(bean.data.share.logo);
        return sendBean;
    }

    /**
     * 图文详情
     */
    private void initDs() {
        ds_root.setDragSwitchListener(new DragSwitchLayout.DragSwitchListener() {
            @Override
            public void onDragToBottomView() {
                if (!isFirstDesc) {
                    isFirstDesc = true;
                    getChildFragmentManager().beginTransaction().add(R.id.fg_desc, WebViewFragment.getInstance(url, false)).commit();
                }
                ((DefaultTitleBarView) getTitleBar()).setTitleContent("图文详情");
            }

            @Override
            public void onDragToTopView() {
                ((DefaultTitleBarView) getTitleBar()).setTitleContent("商品详情");
            }
        });
    }

    private boolean isFirstDesc;

    private boolean isCollect;

    private void initCollect(boolean b) {
        isCollect = b;
        tv_collect.setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(isCollect ? R.mipmap.shoucang_dianji : R.mipmap.shoucang_weidainji)), null, null);
    }


    /**
     * 评价
     *
     * @param comment
     */
    private void initComment(ArrayList<GoodEvaluateBean.Data> comment) {
        rv_comment.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv_comment.setAdapter(new GoodEvaluateAdapter(getContext(), comment));
    }

    /**
     * 商品规格
     *
     * @param attrs
     */
    private void initAttr(ArrayList<AttrsBean> attrs) {
        if (attrs.size() % 2 != 0) {
            attrs.add(new AttrsBean());
        }


        rv_attr.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv_attr.setAdapter(new AttrAdapter(getContext(), attrs));
    }

    /**
     * 商品信息
     *
     * @param data
     */
    private void initGoodInfo(GoodDescBean.Data data) {
        tv_good_name.setText(data.goodsName);
        tv_price_type.setText(data.type == 0 ? "商品价" : "团购价");
        tv_time.setVisibility(data.type == 0 ? View.GONE : View.VISIBLE);
        if (data.type == 2) {
            managerTuan(data);
        }

        tv_price.setText("￥" + data.marketPrice);
        if (!TextUtils.isEmpty(data.goodsTips)) {
            tv_introduce.setVisibility(View.VISIBLE);
            tv_introduce.setText(data.goodsTips);
        }
        tv_old_price.setText("￥" + data.shopPrice);
        tv_count.setText("已售" + data.saleNum + "笔");
        tv_all_evaluate.setText(data.appraiseNum == 0 ? "暂无评论" : "全部评论");
    }

    private CountDownTimer timer;

    private void managerTuan(GoodDescBean.Data data) {
        Log.d("时间", System.currentTimeMillis() + "");
        Log.d("时间", data.endtime + "");
        long time = data.endtime * 1000 - System.currentTimeMillis();
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long time) {
                tv_time.setText("距离团购结束" + TimeUtils.getTime(time));
            }

            @Override
            public void onFinish() {
//                MyToast.showToast("团购结束");
                tv_time.setVisibility(View.GONE);
                tv_price_type.setText("商品价");
            }
        };

        timer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * 轮播图
     *
     * @param gallery
     */
    private void initAd(ArrayList<GoodDescBean.Data.GalleryBean> gallery) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rpv_ad.getLayoutParams();
        params.height = Util.getWidth();
        rpv_ad.setLayoutParams(params);
        rpv_ad.setPlayDelay(gallery.size() > 1 ? 4000 : 0);
        rpv_ad.setHintView(new ColorPointHintView(getContext(), Color.parseColor("#ee9821"), Color.WHITE));
        rpv_ad.setAdapter(new MyLoopPagerAdapter(rpv_ad, gallery));
        rpv_ad.getViewPager().setPageTransformer(true, new AccordionTransformer());
    }

    @Override
    protected String url() {
        return ApiConstant.GOOD_DESC;
    }

    @Override
    protected Map<String, String> map() {
        map.put("id", good_id);
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<GoodDescBean> getTClass() {
        return GoodDescBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_good_desc, null);
        ButterKnife.bind(this, view);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        return view;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("商品详情")
                .setRightImage(R.mipmap.fenxiang).setOnTitleBarListener(this);
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @OnClick(R.id.tv_shop)
    void showShop() {
        RxBus.get().post("addFragment", new AddFragmentBean(ShopInfoFragment.getInstance(shopId)));
    }

    @OnClick(R.id.tv_all_evaluate)
    void allEvaluate() {
        RxBus.get().post("addFragment", new AddFragmentBean(GoodEvaluateFragment.getInstance("goodsId," + good_id)));
    }


    @OnClick(R.id.tv_buy)
    void pay() {
        BuyCarController.getInstance().addBuyCar(false, new OnSingleRequestListener<AddCarBean>() {
            @Override
            public void succes(boolean isWrite, AddCarBean bean) {
                RxBus.get().post("addFragment", new AddFragmentBean(AffirmBuyFragment.getInstance("[{\"cartId\":\"" + bean.data.cartId + "\"}]")));
            }

            @Override
            public void error(boolean isWrite, AddCarBean bean, String msg) {

            }
        }, good_id, bean.data.sid, "1");
    }

    @OnClick(R.id.tv_add_car)
    void addCar() {
        if (bean.data.spec == null || bean.data.spec.size() == 0) {
            BuyCarController.getInstance().addBuyCar(true, null, good_id, bean.data.sid, 1 + "");

        } else {
            new PopBuyCar(getContext(), bean.data.spec, bean.data.saleSpec, bean.data.gallery.get(0).getUrl(), bean.data.isDefault) {
                @Override
                protected void addBuyCar(String sid, String count) {
                    BuyCarController.getInstance().addBuyCar(true, null, good_id, sid, count);
                }
            }.showAtLocation(false);
        }

    }

    @OnClick(R.id.tv_online)
    void online() {
        GoodDescBean.Data.Serviceid serviceid = bean.data.serviceid;
        ChatFriendBean select = ChatFriendsImpl.getInstance().select(serviceid.shopUserId);
        if (select == null) {
            select = new ChatFriendBean();
            select.setName(serviceid.shopUserName);
            select.setFid(serviceid.shopUserId);
            select.setGid("-1");
            select.setType(2);
            select.setLogo(serviceid.shopUserPhoto);
            ChatFriendsImpl.getInstance().add(select);
        }
        RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(serviceid.shopUserId, true)));

    }

    @OnClick(R.id.iv_car)
    void buyCar() {
        RxBus.get().post("addFragment", new AddFragmentBean(new BuyCarRootFragment()));
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        if (isCanShape) {
            showShare();
        }

    }

    @Override
    public void center() {

    }


    @OnClick(R.id.tv_collect)
    void collect() {
        if (isCollect) {
            GoodController.getInstance().cancelCollect(good_id, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    isCollect = false;
                    tv_collect.setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(isCollect ? R.mipmap.shoucang_dianji : R.mipmap.shoucang_weidainji)), null, null);

                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        } else {
            GoodController.getInstance().addCollect(good_id, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    isCollect = true;
                    tv_collect.setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(isCollect ? R.mipmap.shoucang_dianji : R.mipmap.shoucang_weidainji)), null, null);

                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        }
    }


    @Override
    protected ShareUtil.ShareInfo getShareInfo() {
        ShareUtil.ShareInfo shareInfo = new ShareUtil.ShareInfo();
        shareInfo.content = bean.data.share.info;
        shareInfo.title = bean.data.share.title;
        shareInfo.logo = bean.data.share.logo;
        shareInfo.url = bean.data.share.url;
        return shareInfo;
    }


    @Override
    protected String getImg() {
        return bean.data.share.logo;
    }

    @Override
    public String getGoodsId() {
        return good_id;
    }

    @Override
    protected String getGoodsName() {
        return bean.data.goodsName;
    }
}
