package haoshi.com.shop.fragment.index;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.recker.flyshapeimageview.ShapeImageView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.bean.my.PeosonCenterBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.discover.DiscoverCollectRootFragment;
import haoshi.com.shop.fragment.discover.MyDiscoverSendFragment;
import haoshi.com.shop.fragment.my.GeRenAuthentFragment;
import haoshi.com.shop.fragment.my.MessageFragment;
import haoshi.com.shop.fragment.my.MyGiveFragment;
import haoshi.com.shop.fragment.my.PeosonInfoFragment;
import haoshi.com.shop.fragment.my.PeosonSetFragment;
import haoshi.com.shop.fragment.my.QiYeAuthentFragment;
import haoshi.com.shop.fragment.my.SetFragment;
import haoshi.com.shop.fragment.my.ZanFragment;
import haoshi.com.shop.fragment.shop.GoodCollectFragment;
import haoshi.com.shop.fragment.shop.MyOrderRootFragment;
import haoshi.com.shop.fragment.zongqinghui.ApplyBuildFlockFragment;
import haoshi.com.shop.pop.PopContactService;
import haoshi.com.shop.pop.PopRenZTip;
import haoshi.com.shop.R;
import rx.Observable;
import rx.functions.Action1;
import util.DrawableUtil;
import util.GlideUtil;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/1/16.
 */

public class IndexFiveFragment extends SingleNetWorkBaseFragment<PeosonCenterBean> {
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.rv_item)
    RecyclerView rv_item;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_head)
    ShapeImageView iv_head;
    @BindView(R.id.iv_set)
    ImageView iv_set;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_check)
    TextView tv_check;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_ping)
    TextView tv_ping;

    @Override
    protected String url() {
        return ApiConstant.USERINTERFACE;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    private PeosonCenterBean.Data data;

    @Override
    protected void writeData(boolean isWrite, PeosonCenterBean bean) {
        super.writeData(isWrite, bean);
        this.data = bean.getData();
        initInfo();
        initOrder();
        initItem();
    }

    @Override
    protected Class<PeosonCenterBean> getTClass() {
        return PeosonCenterBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_five, null);
        ButterKnife.bind(this, view);
        initGetDataRxBus();
        return view;
    }


    @Override
    protected View getTitleBarView() {
        return null;
    }

    private int checkStatus;

    private void initInfo() {
        GlideUtil.GlideErrAndOc(getContext(), data.userPhoto, iv_bg);
        GlideUtil.GlideErrAndOc(getContext(), data.userPhoto, iv_head);
        tv_name.setText(data.userName);
        Drawable d = null;
        if (data.userSex != 0) {
            d = DrawableUtil.setBounds(getResources().getDrawable(data.userSex == 1 ? R.mipmap.wode_boy : R.mipmap.wode_boy));
        }
        tv_name.setCompoundDrawables(null, null, d, null);

        tv_message.setText(data.notice + "\n通知");
        tv_zan.setText(data.praise + "\n赞");
        tv_ping.setText(data.comment + "\n评论");


        if (data.isTrue == 1) {
            if (data.shops == 1) {
                checkStatus = 1;
                tv_check.setText("企业认证");//企业已认证
            } else if (data.person == 1) {
                checkStatus = 2;
                tv_check.setText("个人认证");//已完成个人认证
            } else {
                checkStatus = 3;
                tv_check.setText("未认证");
            }
        } else {
            checkStatus = 4;
            tv_check.setText("信息未完善");
        }

    }


    @OnClick(R.id.tv_check)
    void check() {
        switch (checkStatus) {
            case 1:
                RxBus.get().post("addFragment", new AddFragmentBean(QiYeAuthentFragment.getInstance(true)));
                break;
            case 2:
                RxBus.get().post("addFragment", new AddFragmentBean(GeRenAuthentFragment.getInstance(true)));
                break;
            case 3:
                new PopRenZTip(getContext()).showAtLocation(false);
                break;
            case 4:
                RxBus.get().post("addFragment", new AddFragmentBean(new PeosonInfoFragment()));
                break;
        }

    }

    private void initOrder() {
        final ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("待付款", R.mipmap.wode_daifukuan, null, data.dfk, 1));
        datas.add(new GeneralBean("待发货", R.mipmap.wode_fahuo, null, data.dfh, 1));
        datas.add(new GeneralBean("已发货", R.mipmap.wode_yi, null, data.yfh, 1));
        datas.add(new GeneralBean("待评价", R.mipmap.wode_pingjia, null, data.dpj, 1));
        datas.add(new GeneralBean("退款", R.mipmap.wode_tui, null, data.tk, 1));
        GridLayoutManager manager = new GridLayoutManager(getContext(), 5) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GeneralAdapter mAdatpter = new GeneralAdapter(getContext(), datas) {
            @Override
            protected void chooseItem(int position) {
                if (position != datas.size() - 1) {
                    RxBus.get().post("addFragment", new AddFragmentBean(MyOrderRootFragment.getInstance(position + 1)));
                } else {
                    MyToast.showToast("退款建设中");
                }
            }
        };
        rv_order.setAdapter(mAdatpter);
        rv_order.setLayoutManager(manager);
    }

    //    MyDiscoverSendFragment
    private void initItem() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("我已发布", R.mipmap.wode_roll, null, 2));
        datas.add(new GeneralBean("我的商品收藏", R.mipmap.shop, null, 2));
        datas.add(new GeneralBean("我的发现收藏", R.mipmap.find, null, 2));
        datas.add(new GeneralBean("我的打赏", R.mipmap.wode_jilu, null, 2));
        datas.add(new GeneralBean("申请建群", R.mipmap.wode_jiaqun, null, 2));
        datas.add(new GeneralBean("用户协议", R.mipmap.wode_xieyi, null, 2));
        datas.add(new GeneralBean("联系我们", R.mipmap.wode_lianxi, null, 2));
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GeneralAdapter mAdatpter = new GeneralAdapter(getContext(), datas) {
            @Override
            protected void chooseItem(int position) {
                switch (position) {
                    case 0:
                        RxBus.get().post("addFragment", new AddFragmentBean(new MyDiscoverSendFragment()));
                        break;
                    case 1:
                        RxBus.get().post("addFragment", new AddFragmentBean(new GoodCollectFragment()));
                        break;
                    case 2:
                        RxBus.get().post("addFragment", new AddFragmentBean(new DiscoverCollectRootFragment()));
                        break;
                    case 3:
                        RxBus.get().post("addFragment", new AddFragmentBean(new MyGiveFragment()));
                        break;
                    case 4:
                        RxBus.get().post("addFragment", new AddFragmentBean(new ApplyBuildFlockFragment()));
                        break;
                    case 6:
                        new PopContactService(ctx).showAtLocation(false);
                        break;
                }
            }
        };
        rv_item.setAdapter(mAdatpter);
        rv_item.setLayoutManager(manager);
    }


    @OnClick(R.id.tv_my_order)
    void myOrder() {
        RxBus.get().post("addFragment", new AddFragmentBean(MyOrderRootFragment.getInstance(0)));
    }


    @OnClick(R.id.iv_set)
    void set() {
        RxBus.get().post("addFragment", new AddFragmentBean(new SetFragment()));
    }

    @OnClick(R.id.tv_message)
    void message() {
        RxBus.get().post("addFragment", new AddFragmentBean(new MessageFragment()));
    }

    @OnClick(R.id.tv_zan)
    void zan() {
        RxBus.get().post("addFragment", new AddFragmentBean(new ZanFragment()));
    }

    @OnClick(R.id.tv_ping)
    void ping() {
        RxBus.get().post("addFragment", new AddFragmentBean(new CommentFragment()));
    }

    @OnClick(R.id.iv_head)
    void head() {
        RxBus.get().post("addFragment", new AddFragmentBean(new PeosonSetFragment()));
    }


    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    private Observable<String> getDataRxbus;

    private void initGetDataRxBus() {
        if (getDataRxbus == null) {
            getDataRxbus = RxBus.get().register("fivegetdata", String.class);
            getDataRxbus.subscribe(new Action1<String>() {
                @Override
                public void call(String integer) {
                    getData();
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("fivegetdata", getDataRxbus);
    }
}
