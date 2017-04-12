package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.XLHRatingBar;

/**
 * Created by dengmingzhi on 2017/3/23.
 */

public class OrderCommentFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.ratingBar)
    XLHRatingBar ratingBar;
    @BindView(R.id.tv_count)
    TextView tv_count;
    private int position;

    public static OrderCommentFragment getInstance(String id, int position) {
        OrderCommentFragment fragment = new OrderCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            position = bundle.getInt("position");

        }
    }

    @Override
    protected String url() {
        return ApiConstant.ORDER_ADD_COMMENT;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    private int count = 5;
    private String content;

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("orderId", id);
        map.put("goodsScore", count + "");
        map.put("content", content);
        return super.map();
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        RxBus.get().post("back", "back");
        RxBus.get().post("orderCommentRxBus", position);
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_order_comment, null);
        ButterKnife.bind(this, view);
        ratingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                count = countSelected;
                tv_count.setText(countSelected + ".0分");
            }
        });
        return view;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("发表评价")
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

    @OnClick(R.id.bt_submit)
    void submit() {
        content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            MyToast.showToast("请填写评论");
            return;
        }
        if (count < 1) {
            MyToast.showToast("请至少选择1星");
            return;
        }

        getData();
    }

    @Override
    protected String getnMsg(int code) {
        switch (code) {
            case 10052:
                return "订单状态已改变";
            case 10055:
                return "已评价过该订单";
            case 10051:
                return "订单无效";
            case 10002:
                return "评论出错";
            case 10001:
                return "评论失败";
        }

        return super.getnMsg(code);
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("提交评价", "提交成功", "提交失败");
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }
}
