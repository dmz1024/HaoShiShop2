package haoshi.com.shop.pop;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jph.takephoto.model.TException;

import java.util.ArrayList;

import base.bean.ChooseStringBean;
import base.other.PopBaseView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.OrderIdBean;
import haoshi.com.shop.controller.DiscoverController;
import haoshi.com.shop.controller.MyOrderController;
import haoshi.com.shop.pay.PayController;
import haoshi.com.shop.pay.PayUtil;
import interfaces.OnSingleRequestListener;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import util.Util;
import view.pop.ChooseStringView;

/**
 * Created by dengmingzhi on 2017/2/22.
 */

public class PopContactDiscoverPerson extends PopBaseView implements View.OnClickListener {

    public PopContactDiscoverPerson(Context ctx) {
        super(ctx);
    }

    private String[] content;
    private boolean isFrom;

    public PopContactDiscoverPerson(Context ctx, String... content) {
        super(ctx);
        this.content = content;
    }

    public PopContactDiscoverPerson(Context ctx, boolean isFrom, String... content) {
        super(ctx);
        this.content = content;
        this.isFrom = isFrom;
    }

    @Override
    protected int getAnimation() {
        return com.mall.naiqiao.mylibrary.R.style.small_2_big;
    }

    private View view;
    private ViewStub subPhone;
    private View inflateChooseMoney;

    @Override
    protected View getView() {

        view = View.inflate(ctx, R.layout.pop_contact_discover_person, null);
        view.findViewById(R.id.iv_cancel).setOnClickListener(this);
        final ViewStub subChooseMoney = (ViewStub) view.findViewById(R.id.sb_choose_money);
        subPhone = (ViewStub) view.findViewById(R.id.sb_phone);

        final View in_safe_tipsView = view.findViewById(R.id.in_safe_tips);
        in_safe_tipsView.findViewById(R.id.bt_affirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_safe_tipsView.setVisibility(View.GONE);
                inflateChooseMoney = subChooseMoney.inflate();
                initChooseMoney(inflateChooseMoney);
            }

        });
        if (isFrom) {
            TextView tv_tip = (TextView) in_safe_tipsView.findViewById(R.id.tv_tip);
            TextView tv_tip_content = (TextView) in_safe_tipsView.findViewById(R.id.tv_tip_content);
            tv_tip.setText("打赏");
            tv_tip_content.setText("亲人们，犒劳一下我呗！");
        }
//        showMoneyType(true);
        return view;
    }

    private String money = "0.99";

    @Override
    public void onClick(View view) {
        if (view == tv_money_1 || view == tv_money_2 || view == tv_money_3) {
            changeMoneyColor((TextView) view);
        } else if (view.getId() == R.id.iv_cancel) {
            dismiss();
        } else if (view == tv_contact) {
            initPhone();
        } else if (view == tv_call_phone) {
            Util.tel(ctx, content[0]);
            showComment();
        } else if (view == tv_call_sms) {
            Util.sms(ctx, content[0], "【郝氏商城】您好，我看了您的文章:" + content[1]);

            showComment();
        } else if (view == tv_call_comment) {
            dismiss();
            goComment();
        } else if (view == bt_pay) {
            initPayRxBus();
            DiscoverController.getInstance().getDssubmitId(new OnSingleRequestListener<OrderIdBean>() {
                @Override
                public void succes(boolean isWrite, final OrderIdBean bean) {
                    final ArrayList<ChooseStringBean> datas = new ArrayList<>();
                    datas.add(new ChooseStringBean("支付宝安全支付"));
                    datas.add(new ChooseStringBean("微信安全支付"));
                    new ChooseStringView<ChooseStringBean>(ctx, datas) {
                        @Override
                        protected void itemClick(final int position) {
                            super.itemClick(position);
                            switch (position) {
                                case 0:
                                    PayController.getInstance().ali(bean.data.orderId, "1");
                                    break;
                                case 1:
                                    MyToast.showToast("微信支付正在建设中。。。", 5000);
                                    break;
                            }
                        }

                        @Override
                        protected float getAlpha() {
                            return 1f;
                        }
                    }.showAtLocation(false);
                }

                @Override
                public void error(boolean isWrite, OrderIdBean bean, String msg) {

                }
            }, content[2], money);
        }
    }

    protected void goComment() {
    }


    private void showComment() {
        ll_phone.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_phone_num.setTextSize(20f);
                tv_phone_num.setText("聊得怎么样？来评价一下吧");
                ll_phone.setVisibility(View.GONE);
                tv_call_comment.setVisibility(View.VISIBLE);
            }
        }, 1000);

    }


    private View inflatePhone;
    private View ll_phone;

    private void initPhone() {
        inflateChooseMoney.setVisibility(View.GONE);
        inflatePhone = subPhone.inflate();
        tv_call_phone = (TextView) inflatePhone.findViewById(R.id.tv_call_phone);
        ll_phone = inflatePhone.findViewById(R.id.ll_phone);
        tv_call_sms = (TextView) inflatePhone.findViewById(R.id.tv_call_sms);
        tv_phone_num = (TextView) inflatePhone.findViewById(R.id.tv_phone_num);
        TextView tv_phone_name = (TextView) inflatePhone.findViewById(R.id.tv_phone_name);
        tv_call_comment = (TextView) inflatePhone.findViewById(R.id.tv_call_comment);
        tv_call_phone.setOnClickListener(this);
        tv_call_comment.setOnClickListener(this);
        tv_call_sms.setOnClickListener(this);
        tv_phone_num.setText(content[0]);
        tv_phone_name.setText(content[3]);
    }

    private TextView tv_call_phone;
    private TextView tv_call_sms;

    private TextView tv_update_money;
    private LinearLayout ll_choose_money;
    private TextView tv_money;
    private TextView tv_money_1;
    private TextView tv_money_3;
    private TextView tv_money_2;
    private TextView tv_contact;
    private TextView tv_call_comment;
    private TextView tv_phone_num;
    private Button bt_pay;

    /**
     * 选择打赏金额
     *
     * @param inflate
     */
    private void initChooseMoney(View inflate) {

        tv_contact = (TextView) inflate.findViewById(R.id.tv_contact);
        ll_choose_money = (LinearLayout) inflate.findViewById(R.id.ll_choose_money);
//        tv_update_money.setOnClickListener(this);
        if (isFrom) {
            tv_contact.setVisibility(View.GONE);
        } else {
            tv_contact.setOnClickListener(this);
        }


        tv_money_1 = (TextView) inflate.findViewById(R.id.tv_money_1);
        tv_money_2 = (TextView) inflate.findViewById(R.id.tv_money_2);
        tv_money_3 = (TextView) inflate.findViewById(R.id.tv_money_3);
        bt_pay = (Button) inflate.findViewById(R.id.bt_pay);
        tv_money_1.setOnClickListener(this);
        tv_money_2.setOnClickListener(this);
        tv_money_3.setOnClickListener(this);
        bt_pay.setOnClickListener(this);

    }


    private void showMoneyType(boolean visi) {
        tv_money.setVisibility(visi ? View.GONE : View.VISIBLE);
        tv_update_money.setVisibility(visi ? View.GONE : View.VISIBLE);
        ll_choose_money.setVisibility(visi ? View.VISIBLE : View.GONE);
    }

    private void changeMoneyColor(TextView tv) {
        tv_money_1.setBackgroundResource(R.drawable.rectangle_fff_e2e2e2);
        tv_money_2.setBackgroundResource(R.drawable.rectangle_fff_e2e2e2);
        tv_money_3.setBackgroundResource(R.drawable.rectangle_fff_e2e2e2);
        tv_money_1.setTextColor(Color.parseColor("#666666"));
        tv_money_2.setTextColor(Color.parseColor("#666666"));
        tv_money_3.setTextColor(Color.parseColor("#666666"));
        tv.setTextColor(Color.parseColor("#ee9821"));
        tv.setBackgroundResource(R.drawable.rectangle_00000000_ee9821);
//        showMoneyType(false);
//        tv_money.setText("￥" + tv.getText().toString());
        money = tv.getText().toString();
    }

    private Observable<Integer> payRxBus;

    private void initPayRxBus() {
        PayUtil.PAYRESULT = 60000;
        if (payRxBus == null) {
            payRxBus = RxBus.get().register("payRxBus", Integer.class);
            payRxBus.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer s) {
                    if (s == 60000) {
                        if (isFrom) {
                            dismiss();
                        } else {
                            initPhone();
                        }

                    }
                }
            });
        }

    }

    @Override
    public void onDismiss() {
        super.onDismiss();
        RxBus.get().unregister("payRxBus", payRxBus);
    }
}
