package haoshi.com.shop.pop;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import base.other.PopBaseView;
import haoshi.com.shop.R;
import util.Util;

/**
 * Created by dengmingzhi on 2017/2/22.
 */

public class PopContactDiscoverPerson extends PopBaseView implements View.OnClickListener {

    public PopContactDiscoverPerson(Context ctx) {
        super(ctx);
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
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == tv_update_money) {
            showMoneyType(true);
        } else if (view == tv_money_1 || view == tv_money_2 || view == tv_money_3) {
            changeMoneyColor((TextView) view);
        } else if (view.getId() == R.id.iv_cancel) {
            dismiss();
        } else if (view == tv_contact) {
            initPhone();
        } else if (view == tv_call_phone) {
            Util.tel(ctx, "18326167257");
            showComment();
        } else if (view == tv_call_sms) {
            Util.sms(ctx, "18515982882", "您好，我看了您的文章........");
            showComment();
        }else if(view==tv_call_comment){
            dismiss();
            goComment();
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
        },1000);

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
        tv_call_comment = (TextView) inflatePhone.findViewById(R.id.tv_call_comment);
        tv_call_phone.setOnClickListener(this);
        tv_call_comment.setOnClickListener(this);
        tv_call_sms.setOnClickListener(this);
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

    /**
     * 选择打赏金额
     *
     * @param inflate
     */
    private void initChooseMoney(View inflate) {
        tv_update_money = (TextView) inflate.findViewById(R.id.tv_update_money);
        tv_money = (TextView) inflate.findViewById(R.id.tv_money);
        tv_contact = (TextView) inflate.findViewById(R.id.tv_contact);
        ll_choose_money = (LinearLayout) inflate.findViewById(R.id.ll_choose_money);
        tv_update_money.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        tv_money_1 = (TextView) inflate.findViewById(R.id.tv_money_1);
        tv_money_2 = (TextView) inflate.findViewById(R.id.tv_money_2);
        tv_money_3 = (TextView) inflate.findViewById(R.id.tv_money_3);
        tv_money_1.setOnClickListener(this);
        tv_money_2.setOnClickListener(this);
        tv_money_3.setOnClickListener(this);

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
        showMoneyType(false);
        tv_money.setText("￥" + tv.getText().toString());
    }


}
