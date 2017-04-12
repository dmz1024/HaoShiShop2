package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.ChooseStringBean;
import base.bean.SingleBaseBean;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.MyOrderBean;
import haoshi.com.shop.controller.MyOrderController;
import haoshi.com.shop.fragment.shop.OrderCommentFragment;
import haoshi.com.shop.fragment.shop.OrderDescFragment;
import interfaces.OnSingleRequestListener;
import haoshi.com.shop.pay.PayController;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import view.pop.ChooseStringView;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class MyOrderAdapter extends BaseAdapter<MyOrderBean.Data> {
    public MyOrderAdapter(Context ctx, ArrayList<MyOrderBean.Data> list) {
        super(ctx, list);
        initOrderRxBus();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_my_order, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        MyOrderBean.Data data = list.get(position);
        mHolder.tv_shop_name.setText(data.shopName);
        mHolder.tv_status.setText(data.status);
        mHolder.tv_price.setText(data.needPay);
        inintGoods(mHolder.rv_content, data.list);
        manager(mHolder.tv_left, mHolder.tv_right, data.orderStatus, position);
    }

    /**
     * 处理按钮显示
     *
     * @param tv_left
     * @param tv_right
     * @param orderStatus
     */
    private void manager(TextView tv_left, TextView tv_right, int orderStatus, final int position) {
        tv_left.setVisibility(View.GONE);
        tv_right.setVisibility(View.GONE);
        tv_left.setOnClickListener(null);
        tv_right.setOnClickListener(null);
        switch (orderStatus) {
            case 16:
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_left.setText("取消订单");
                tv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOrderController.getInstance().cancelOrder(list.get(position).orderId, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                list.get(position).orderStatus = 15;
                                list.get(position).status = "已取消";
                                notifyDataSetChanged();
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
                        final ArrayList<ChooseStringBean> datas = new ArrayList<>();
                        datas.add(new ChooseStringBean("支付宝安全支付"));
                        datas.add(new ChooseStringBean("微信安全支付"));
                        new ChooseStringView<ChooseStringBean>(ctx, datas) {
                            @Override
                            protected void itemClick(int p) {
                                super.itemClick(p);
                                switch (p) {
                                    case 0:
                                        PayController.getInstance().ali(list.get(position).orderId);
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
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("催单");
                break;
            case 6:
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("确认收货");
                tv_left.setText("查看物流");
                break;
            case 12:
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("评价");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RxBus.get().post("addFragment", new AddFragmentBean(OrderCommentFragment.getInstance(list.get(position).orderId, position)));
                    }
                });
                break;
            case 7:
            case 11:
            case 8:
            case 15:
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("删除订单");
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOrderController.getInstance().deleteOrder(list.get(position).orderId, new OnSingleRequestListener<SingleBaseBean>() {
                            @Override
                            public void succes(boolean isWrite, SingleBaseBean bean) {
                                remove(position);
                            }

                            @Override
                            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

                            }
                        });
                    }
                });
                break;
            case 10:
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("取消退款");
                break;

        }
    }

    private void inintGoods(RecyclerView rv_content, ArrayList<MyOrderBean.Data.OrderGoodBean> list) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        MyOrderGoodAdapter mAdapter = new MyOrderGoodAdapter(ctx, list);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_left)
        TextView tv_left;
        @BindView(R.id.tv_right)
        TextView tv_right;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment", new AddFragmentBean(OrderDescFragment.getInstance(list.get(layoutPosition).orderId, layoutPosition, list.get(layoutPosition).status)));
        }
    }


    private Observable<Integer> orderCommentRxBus;
    private Observable<Integer> orderCancelRxBus;
    private Observable<Integer> orderDeleteRxBus;
    private Observable<Integer> orderPayRxBus;
    private Observable<Integer> orderBackRxBus;
    private Observable<Integer> orderReceRxBus;


    private void initOrderRxBus() {
        //评价
        if (orderCommentRxBus == null) {
            orderCommentRxBus = RxBus.get().register("orderCommentRxBus", Integer.class);
            orderCommentRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    list.get(integer).orderStatus = 7;
                    list.get(integer).status = "已完成";
                    notifyDataSetChanged();
                }
            });
        }
        //取消订单
        if (orderCancelRxBus == null) {
            orderCancelRxBus = RxBus.get().register("orderCancelRxBus", Integer.class);
            orderCancelRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    list.get(integer).orderStatus = 15;
                    list.get(integer).status = "已取消";
                    notifyDataSetChanged();
                }
            });
        }

        //删除订单
        if (orderDeleteRxBus == null) {
            orderDeleteRxBus = RxBus.get().register("orderDeleteRxBus", Integer.class);
            orderDeleteRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    remove(integer);
                }
            });
        }
        //支付
        if (orderPayRxBus == null) {
            orderPayRxBus = RxBus.get().register("orderPayRxBus", Integer.class);
            orderPayRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    list.get(integer).orderStatus = 9;
                    list.get(integer).status = "待发货";
                    notifyDataSetChanged();
                }
            });
        }
        //退款
        if (orderBackRxBus == null) {
            orderBackRxBus = RxBus.get().register("orderBackRxBus", Integer.class);
            orderBackRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    list.get(integer).orderStatus = 10;
                    list.get(integer).status = "退款中";
                    notifyDataSetChanged();
                }
            });
        }
        //确认收货
        if (orderReceRxBus == null) {
            orderReceRxBus = RxBus.get().register("orderReceRxBus", Integer.class);
            orderReceRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    list.get(integer).orderStatus = 12;
                    list.get(integer).status = "待评价";
                    notifyDataSetChanged();
                }
            });
        }

    }


    public void cancelRxBus() {
        RxBus.get().unregister("orderCommentRxBus", orderCommentRxBus);
        RxBus.get().unregister("orderCancelRxBus", orderCancelRxBus);
        RxBus.get().unregister("orderDeleteRxBus", orderDeleteRxBus);
        RxBus.get().unregister("orderPayRxBus", orderPayRxBus);
        RxBus.get().unregister("orderBackRxBus", orderBackRxBus);
        RxBus.get().unregister("orderReceRxBus", orderReceRxBus);
    }

}
