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
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.MyOrderGoodAdapter;
import haoshi.com.shop.adapter.OrderDescLogAdapter;
import haoshi.com.shop.bean.shop.OrderDescBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.MyOrderController;
import haoshi.com.shop.fragment.chat.ChatViewFragment;
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

/**
 * Created by dengmingzhi on 2017/3/20.
 */

public class OrderDescFragment extends SingleNetWorkBaseFragment<OrderDescBean> implements OnTitleBarListener {
    private String id;
    private String status;
    private int position;
    @BindView(R.id.tv_address_name)
    TextView tv_address_name;
    @BindView(R.id.tv_address_tel)
    TextView tv_address_tel;
    @BindView(R.id.tv_address_desc)
    TextView tv_address_desc;
    @BindView(R.id.tv_shop_name)
    TextView tv_shop_name;
    @BindView(R.id.rv_status)
    RecyclerView rv_status;
    @BindView(R.id.rv_shop)
    RecyclerView rv_shop;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.tv_left)
    TextView tv_left;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.rl_show)
    RelativeLayout rl_show;

    public static OrderDescFragment getInstance(String id, int position, String status, int rootPosition) {
        OrderDescFragment fragment = new OrderDescFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("status", status);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        position = bundle.getInt("position");
        status = bundle.getString("status");
    }

    @Override
    protected String url() {
        return ApiConstant.ORDER_DESC;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("orderId", id);
        return super.map();
    }

    @Override
    protected Class<OrderDescBean> getTClass() {
        return OrderDescBean.class;
    }


    private OrderDescBean.Data data;

    @Override
    protected void writeData(boolean isWrite, OrderDescBean bean) {
        super.writeData(isWrite, bean);
        this.data = bean.getData();
        initAddress();
        initInfo();
        initLog();
        initShopInfo();
    }



    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return isWriteData ? new TipLoadingBean("", "", "") : super.getTipLoadingBean();
    }

    @Override
    protected boolean showInfo() {
        return false;
    }

    @Override
    protected boolean showSucces() {
        return false;
    }

    /**
     * 商品信息
     */
    private void initShopInfo() {

        tv_total_price.setText("￥" + data.realTotalMoney);
        tv_price.setText("￥" + data.goodsMoney + "\n\n￥" + data.deliverMoney);
        tv_shop_name.setText(data.shopName);
        rv_shop.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv_shop.setAdapter(new MyOrderGoodAdapter(getContext(), data.goods));
        tv_left.setVisibility(View.GONE);
        tv_right.setVisibility(View.GONE);


        switch (data.orderStatus) {
            case 16:
                rl_show.setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_left.setText("取消订单");
                tv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOrderController.getInstance().cancelOrder(id, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                getData();
                                RxBus.get().post("orderManager", position);
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    }
                });
                tv_right.setText("付款");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initPayRxBus();
                        final ArrayList<ChooseStringBean> datas = new ArrayList<>();
                        datas.add(new ChooseStringBean("支付宝安全支付"));
                        datas.add(new ChooseStringBean("微信安全支付"));
                        new ChooseStringView<ChooseStringBean>(getContext(), datas) {
                            @Override
                            protected void itemClick(int p) {
                                super.itemClick(p);
                                switch (p) {
                                    case 0:
                                        PayController.getInstance().ali(id);
                                        break;
                                    case 1:
                                        MyToast.showToast("微信支付建设中");
                                        break;
                                }
                            }
                        }.showAtLocation(false);
                    }
                });


                break;
            case 9:
                rl_show.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("催单");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserInfo.addUser(data.shopUserId, data.shopUserPhoto, data.shopUserName);
                        RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(data.shopUserId)));
                    }
                });
                tv_left.setText("退款");
                tv_left.setVisibility(View.VISIBLE);
                tv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RxBus.get().post("addFragment", new AddFragmentBean(MyOrderBackFragment.getInstance(data.goods, 0, data.goodsMoney, id, position)));
                    }
                });

                break;
            case 6:
                rl_show.setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("确认收货");
                tv_left.setText("查看物流");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOrderController.getInstance().receives(id, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                getData();
                                RxBus.get().post("orderManager", position);
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    }
                });
                break;
            case 12:
                rl_show.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("评价");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initManagerOrder();
                        RxBus.get().post("addFragment", new AddFragmentBean(OrderCommentFragment.getInstance(id, position)));
                    }
                });
                tv_left.setText("退款/退货");
                tv_left.setVisibility(View.VISIBLE);
                tv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<ChooseStringBean> chooseStringBeen = new ArrayList<ChooseStringBean>();
                        chooseStringBeen.add(new ChooseStringBean("退款"));
                        chooseStringBeen.add(new ChooseStringBean("退货"));
                        new ChooseStringView<ChooseStringBean>(getContext(), chooseStringBeen) {
                            @Override
                            protected void itemClick(int position) {
                                super.itemClick(position);
                                RxBus.get().post("addFragment", new AddFragmentBean(MyOrderBackFragment.getInstance(data.goods, position, data.goodsMoney, id, OrderDescFragment.this.position)));
                            }
                        }.showAtLocation(false);
                    }
                });
                break;
            case 7:
            case 11:
            case 8:
            case 15:
                rl_show.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("删除订单");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOrderController.getInstance().deleteOrder(id, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                RxBus.get().post("orderManager", position);
                                RxBus.get().post("back", "back");
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    }
                });
                break;
            case 10:
                rl_show.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("取消退款");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOrderController.getInstance().cancelOrder(id, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                getData();
                                RxBus.get().post("orderManager", position);
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    }
                });
                break;
        }
    }

    /**
     * 流程信息
     */
    private void initLog() {
        ArrayList<OrderDescBean.Data.LogBean> logs = new ArrayList<>();

        logs.add(new OrderDescBean.Data.LogBean("订单状态", status, "#ee9821"));
        logs.add(new OrderDescBean.Data.LogBean("订单编号", data.orderNo));
        logs.addAll(data.log);

        rv_status.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv_status.setAdapter(new OrderDescLogAdapter(getContext(), logs));
    }

    /**
     * 地址信息
     */
    private void initAddress() {
        tv_address_name.setText("收货人：" + data.userName);
        tv_address_tel.setText(data.userPhone);
        tv_address_desc.setText(data.userAddress);
    }

    /**
     * 基本信息
     */
    private void initInfo() {

    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_order_desc, null);
        ButterKnife.bind(this, view);
        initManagerOrder();
        return view;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("订单详情")
                .setOnTitleBarListener(this);
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

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    private Observable<Integer> payRxBus;

    private void initPayRxBus() {
        PayUtil.PAYRESULT = 30000 + position;
        if (payRxBus == null) {
            payRxBus = PayRxBus.getVavle(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    if (integer == 30000 + position) {
                        RxBus.get().post("orderManager", position);
                        getData();
                    }
                }
            });
        }


    }

    private Observable<String> orderDescRxBus;

    private void initManagerOrder() {
        if (orderDescRxBus == null) {
            orderDescRxBus = RxBus.get().register("orderDescRxBus", String.class);
            orderDescRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {

                    getData();
                }
            });
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("payRxBus", payRxBus);
        RxBus.get().unregister("orderDescRxBus", orderDescRxBus);
    }
}
