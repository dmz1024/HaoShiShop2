package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.ChooseStringBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.AffirmBuyAdapter;
import haoshi.com.shop.bean.shop.AffirmBuyBean;
import haoshi.com.shop.bean.shop.OrderIdBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.MyOrderController;
import haoshi.com.shop.fragment.my.AddressFragment;
import haoshi.com.shop.pay.PayRxBus;
import haoshi.com.shop.pay.PayUtil;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import haoshi.com.shop.pay.PayController;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;
import view.pop.PopEdit;

/**
 * Created by dengmingzhi on 2017/3/20.
 */

public class AffirmBuyFragment extends SingleNetWorkBaseFragment<AffirmBuyBean> implements OnTitleBarListener {
    private String carts;
    private String addressId;
    @BindView(R.id.tv_null_address)
    TextView tv_null_address;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.tv_address_name)
    TextView tv_address_name;
    @BindView(R.id.tv_address_tel)
    TextView tv_address_tel;
    @BindView(R.id.tv_address_desc)
    TextView tv_address_desc;
    @BindView(R.id.tv_note)
    TextView tv_note;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.tv_price)
    TextView tv_price;

    public static AffirmBuyFragment getInstance(String carts) {
        AffirmBuyFragment fragment = new AffirmBuyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("carts", carts);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            carts = bundle.getString("carts");
        }
    }


    @Override
    protected void writeData(boolean isWrite, AffirmBuyBean bean) {
        super.writeData(isWrite, bean);
        initAddress(bean.data.address);
        initGoods(bean.data.shop);
        tv_price.setText(bean.data.goodsTotalMoney);
    }

    private void initGoods(ArrayList<AffirmBuyBean.Data.ShopBean> shop) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        AffirmBuyAdapter mAdapter = new AffirmBuyAdapter(getContext(), shop);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    /**
     * 地址
     *
     * @param address
     */
    private void initAddress(AffirmBuyBean.Data.AddressBean address) {
        if (address == null) {
            tv_null_address.setVisibility(View.VISIBLE);
            rl_address.setVisibility(View.GONE);
        } else {
            addressId = address.addressId;
            tv_null_address.setVisibility(View.GONE);
            rl_address.setVisibility(View.VISIBLE);
            tv_address_name.setText("收货人：" + address.userName);
            tv_address_tel.setText(address.userPhone);
            tv_address_desc.setText(address.areaName + address.userAddress);
        }
    }

    @Override
    protected String url() {
        return ApiConstant.SETTLEMENT;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("carts", carts);

        if (!TextUtils.isEmpty(addressId)) {
            map.put("addressId", addressId);
        }
        return super.map();
    }


    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_affirm_buy, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("确认订单").setOnTitleBarListener(this);
    }

    @OnClick(R.id.tv_note)
    void note() {
        new PopEdit(getContext(), tv_note.getText().toString()) {
            @Override
            protected void content(String content) {
                tv_note.setText(content);
            }
        }.showAtLocation(false);
    }

    @OnClick(R.id.bt_pay)
    void choosePay() {
        if (rl_address.getVisibility() == View.GONE) {
            MyToast.showToast("请选择收货地址");
            return;
        }


        final ArrayList<ChooseStringBean> datas = new ArrayList<>();
        datas.add(new ChooseStringBean("支付宝安全支付"));
        datas.add(new ChooseStringBean("微信安全支付"));
        new ChooseStringView<ChooseStringBean>(getContext(), datas) {
            @Override
            protected void itemClick(final int position) {
                super.itemClick(position);

                initPayRxbus();
                MyOrderController.getInstance().submitOrder(addressId, tv_note.getText().toString(), new OnSingleRequestListener<OrderIdBean>() {
                    @Override
                    public void succes(boolean isWrite, OrderIdBean bean) {
                        switch (position) {
                            case 0:
                                PayController.getInstance().ali(bean.data.orderId);
                                break;
                            case 1:
                                MyToast.showToast("微信支付正在建设中。。。", 5000);
                                break;
                        }

                    }

                    @Override
                    public void error(boolean isWrite, OrderIdBean bean, String msg) {

                    }
                });
            }
        }.showAtLocation(false);
    }

    private Observable<Integer> payRxbus;

    private void initPayRxbus() {
        PayUtil.PAYRESULT = 50000;
        if (payRxbus == null) {
            payRxbus = PayRxBus.getVavle(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    if (integer == 50000) {
                        RxBus.get().post("back", "back");
                    }
                }
            });
        }
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


    @OnClick({R.id.tv_null_address, R.id.rl_address})
    void chooseAddress() {
        initChooseAddressRxBus();
        RxBus.get().post("addFragment", new AddFragmentBean(AddressFragment.getInstance(true)));
    }

    @Override
    protected Class<AffirmBuyBean> getTClass() {
        return AffirmBuyBean.class;
    }

    private Observable<String> chooseAddress;

    private void initChooseAddressRxBus() {
        if (chooseAddress == null) {
            chooseAddress = RxBus.get().register("chooseAddress", String.class);
            chooseAddress.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    addressId = s;
                    getData();
                }
            });
        }
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return TextUtils.isEmpty(addressId) ? null : new TipLoadingBean("获取邮费", "", "获取失败");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("chooseAddress", chooseAddress);
        RxBus.get().unregister("payRxBus", payRxbus);
    }
}
