package haoshi.com.shop.fragment.discover;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.allen.library.SuperTextView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.Map;

import base.adapter.MyLoopPagerAdapter;
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.AllDiscoverCommentAdapter;
import haoshi.com.shop.adapter.AttrAdapter;
import haoshi.com.shop.adapter.DiscoverAdapter;
import haoshi.com.shop.bean.AttrsBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.discover.AllDiscoverCommentBean;
import haoshi.com.shop.bean.discover.DiscoverBean;
import haoshi.com.shop.bean.discover.DiscoverDescBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.DiscoverController;
import haoshi.com.shop.controller.ShareUtil;
import haoshi.com.shop.fragment.BaseShapeFragment;
import haoshi.com.shop.pop.PopContactDiscoverPerson;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.DrawableUtil;
import util.GlideUtil;
import util.MyToast;
import util.RxBus;
import util.Util;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverDescFragment extends BaseShapeFragment<DiscoverDescBean> implements OnTitleBarListener {
    @BindView(R.id.tv_collect)
    TextView tv_collect;
    @BindView(R.id.rpv_ad)
    RollPagerView rpv_ad;
    @BindView(R.id.tv_dis_name)
    TextView tv_dis_name;
    @BindView(R.id.tv_dis_time)
    TextView tv_dis_time;
    @BindView(R.id.tv_dis_count)
    TextView tv_dis_count;
    @BindView(R.id.rv_attr)
    RecyclerView rv_attr;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    @BindView(R.id.tv_all_comment)
    TextView tv_all_comment;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.iv_discover_person)
    ImageView iv_discover_person;

    @BindView(R.id.tv_person)
    SuperTextView tv_person;

    public static DiscoverDescFragment getInstance(String catId) {
        DiscoverDescFragment fragment = new DiscoverDescFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", catId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String goodsId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            goodsId = bundle.getString("goodsId");
//            goodsId = "74";
        }
    }


    @Override
    protected void manageError(boolean isWrite, DiscoverDescBean discoverDescBean, String msg) {
        super.manageError(isWrite, discoverDescBean, msg);
        switch (discoverDescBean.code) {
            case 10001:
                MyToast.showToast("内容已删除");
                RxBus.get().post("back", "back");
        }
    }


    private DiscoverDescBean bean;

    @Override
    protected void writeData(boolean isWrite, DiscoverDescBean bean) {
        super.writeData(isWrite, bean);
        ((DefaultTitleBarView) getTitleBar()).setOnTitleBarListener(this);
        this.bean = bean;
        initDiscoverInfo(bean.data);
        initAttr(bean.data.goodsAttr);

        initAd(bean.data.gallery);
        initComment(bean.data.goodsComent);
        initCollect(bean.data.isShouCang == 1);
        initTuijian(bean.data.goodsTuiJian);
        initDesc(bean.data.goodsDesc);
        initPerson();

    }

    @Override
    protected boolean getIsGood() {
        return false;
    }

    @Override
    protected SendBean getSendBean() {
        SendBean sendBean = new SendBean();
        sendBean.setDesc(bean.data.shareContent);
        sendBean.setName(bean.data.goodsName);
        if (bean.data.gallery == null || bean.data.gallery.size() == 0) {
            sendBean.setImg(bean.data.fx_logo);
        } else {
            sendBean.setImg(bean.data.gallery.get(0).getUrl());
        }
        sendBean.setId(goodsId);
        return sendBean;
    }

    /**
     * 发布人
     */
    private void initPerson() {
        GlideUtil.GlideErrAndOc(getContext(), bean.data.userPhoto, iv_discover_person);
        tv_person.setLeftBottomString("近30天发布" + bean.data.publishNum.publishNum + "条").setLeftTopString(bean.data.userName);

    }

    @OnClick(R.id.tv_person)
    void moreSend() {

    }


    private RichText richText;

    private void initDesc(String content) {
        if (!TextUtils.isEmpty(content)) {
            richText = RichText.from(content).into(tv_desc);
        } else {
            tv_desc.setText("暂无介绍");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (richText != null) {
            richText.clear();
            richText = null;
        }
    }

    private void initTuijian(ArrayList<DiscoverBean.Data> goodsTuiJian) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DiscoverAdapter mAdapter = new DiscoverAdapter(getContext(), goodsTuiJian);
        rv_recommend.setLayoutManager(manager);
        rv_recommend.setAdapter(mAdapter);
    }

    private void initComment(ArrayList<AllDiscoverCommentBean.Data> goodsComent) {
        if (goodsComent == null || goodsComent.size() == 0) {
            tv_all_comment.setText("暂无评论");
        } else {
            tv_all_comment.setText("全部评论");
            rv_comment.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_comment.setAdapter(new AllDiscoverCommentAdapter(getContext(), goodsComent));
        }


    }


    private void initDiscoverInfo(DiscoverDescBean.Data data) {
        tv_dis_name.setText(data.goodsName);
        tv_dis_time.setText(data.createTime);
        tv_dis_count.setText(data.liulan + "次浏览");
    }


    private void initCollect(boolean b) {
        isCollect = b;
        tv_collect.setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(isCollect ? R.mipmap.shoucang_dianji : R.mipmap.shoucang_weidainji)), null, null);
    }

    /**
     * 商品规格
     *
     * @param attrs
     */
    private void initAttr(ArrayList<AttrsBean> attrs) {
        if (attrs != null && attrs.size() > 0) {
            rv_attr.setLayoutManager(new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            rv_attr.setAdapter(new AttrAdapter(getContext(), attrs));
        } else {
            rv_attr.setVisibility(View.GONE);
            tv_info.setVisibility(View.GONE);
        }

    }

    /**
     * 轮播图
     */
    private void initAd(ArrayList<DiscoverDescBean.Data.GalleryBean> gallery) {
        if (gallery == null || gallery.size() == 0) {
            rpv_ad.setVisibility(View.GONE);

        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rpv_ad.getLayoutParams();
            params.height = Util.getWidth();
            rpv_ad.setLayoutParams(params);
            rpv_ad.setPlayDelay(gallery.size() > 1 ? 4000 : 0);
            rpv_ad.setHintView(new ColorPointHintView(getContext(), Color.parseColor("#ee9821"), Color.WHITE));
            rpv_ad.setAdapter(new MyLoopPagerAdapter(rpv_ad, gallery));
            rpv_ad.getViewPager().setPageTransformer(true, new AccordionTransformer());
        }


    }

    @Override
    protected String url() {
        return ApiConstant.DISCOVER_DETAIL;
    }

    @Override
    protected Map<String, String> map() {
        map.put("goodsId", goodsId);
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<DiscoverDescBean> getTClass() {
        return DiscoverDescBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_discover_desc, null);
        ButterKnife.bind(this, view);
        return view;
    }


    private boolean isCollect;

    @OnClick(R.id.tv_collect)
    void collect() {
        if (!isCollect) {
            DiscoverController.getInstance().addCollect(goodsId, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    isCollect = true;
                    tv_collect.setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shoucang_dianji)), null, null);

                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        } else {
            DiscoverController.getInstance().cancelCollect(goodsId, new OnSingleRequestListener<SingleBaseBean>() {
                @Override
                public void succes(boolean isWrite, SingleBaseBean bean) {
                    isCollect = false;
                    tv_collect.setCompoundDrawables(null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shoucang_weidainji)), null, null);
                }

                @Override
                public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                }
            });
        }


    }

    @OnClick(R.id.tv_all_comment)
    void allComment() {
        RxBus.get().post("addFragment", new AddFragmentBean(DiscoverCommentFragment.getInstance(goodsId)));
    }


    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("详情")
                .setRightImage(R.mipmap.fenxiang)
                .setOnTitleBarListener(this);
    }


    @OnClick(R.id.tv_phone)
    void phone() {
        new PopContactDiscoverPerson(getContext()) {
            @Override
            protected void goComment() {
                RxBus.get().post("addFragment", new AddFragmentBean(DiscoverContactCommentFragment.getInstance(goodsId)));
            }
        }.showAtLocation(true);
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
        if (isCanShape) {
            showShare();
        }
    }

    @Override
    public void center() {

    }


    @Override
    protected ShareUtil.ShareInfo getShareInfo() {
        ShareUtil.ShareInfo shareInfo = new ShareUtil.ShareInfo();
        shareInfo.content = bean.data.shareContent;
        shareInfo.title = bean.data.fx_title;
        shareInfo.logo = bean.data.fx_logo;
        shareInfo.url = bean.data.fx_url;
        return shareInfo;
    }
}
