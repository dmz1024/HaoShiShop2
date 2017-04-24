package haoshi.com.shop.fragment.shop;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChoosePhotoAdapter;
import haoshi.com.shop.bean.shop.ChooseWLNameBean;
import haoshi.com.shop.bean.shop.OrderDescBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.DrawableUtil;
import util.MyToast;
import util.RxBus;
import view.Color2Text;
import view.pop.ChooseStringView;
import view.pop.PopEdit;

/**
 * Created by dengmingzhi on 2017/4/18.
 */

public class OrderBackDescFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.tv_submit_info)
    TextView tv_submit_info;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.rl_wuliu)
    RelativeLayout rl_wuliu;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.tv_shenhe)
    Color2Text tv_shenhe;
    @BindView(R.id.ll_write_wuliu)
    LinearLayout ll_write_wuliu;
    @BindView(R.id.view_3)
    View view_3;
    @BindView(R.id.tv_caiwu_tip)
    TextView tv_caiwu_tip;
    @BindView(R.id.et_wl_name)
    TextView et_wl_name;
    @BindView(R.id.et_wl_sn)
    EditText et_wl_sn;
    @BindView(R.id.tv_wuliu_gs)
    TextView tv_wuliu_gs;
    @BindView(R.id.tv_wuliu_sn)
    TextView tv_wuliu_sn;
    @BindView(R.id.tv_caiwu_title)
    Color2Text tv_caiwu_title;
    @BindView(R.id.rv_photo)
    RecyclerView rv_photo;


    public static OrderBackDescFragment getInstance(OrderDescBean.Data data, int position) {
        OrderBackDescFragment fragment = new OrderBackDescFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            data = bundle.getParcelable("data");
            position = bundle.getInt("position");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_order_back_desc;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        initInfo();
        initStatus();


    }

    private OrderDescBean.Data data;

    private void initInfo() {
        tv_submit_info.setText(new StringBuffer()
                .append("售后类型：").append(data.otype == 0 ? "仅退款" : "退款/退货").append("\n")
                .append("退款原因：").append(data.reasonOther).append("\n")
                .append("退款金额：").append(data.backMoney).append("元").append("\n")
                .append("收款账号：").append("退还付款账户").append("\n")
                .append("退款说明：").append(data.refundExplain)
                .toString());
        if (data.voucherImg == null || data.voucherImg.size() == 0) {
            rv_photo.setVisibility(View.GONE);
        } else {
            ChoosePhotoAdapter mAdapter = new ChoosePhotoAdapter(getContext(), data.voucherImg).setCanChoose(false).setMax(3);
            rv_photo.setLayoutManager(new GridLayoutManager(getContext(), 3));
            rv_photo.setAdapter(mAdapter);
        }

    }

    private boolean isWriteWuliu;

    private void initStatus() {
        if (data.otype == 1) {
            switch (data.orderStatus) {
                case 17:
                    tv_shenhe.setVisibility(View.VISIBLE);
                    tv_2.setText("正在审核");
                    tv_2.setTextColor(Color.parseColor("#666666"));
                    tv_2.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont2)), null);
                    break;
                case -1:
                    tv_2.setText("审核失败");
                    tv_shenhe.setContentNotChange("审核失败");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    tv_shenhe.setVisibility(View.VISIBLE);
                    break;
                case 18:
                    rl_wuliu.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(data.orfexpressName)) {
                        tv_2.setText("审核通过\n填写物流");
                        isWriteWuliu = true;
                    } else {
                        rl_back.setVisibility(View.VISIBLE);
                        tv_2.setText("审核通过");
                        tv_wuliu_gs.setText("物流公司：" + data.orfexpressName);
                        tv_wuliu_gs.setText("物流单号：" + data.oexpressNo);
                        tv_3.setText("财务审核");
                        tv_caiwu_tip.setVisibility(View.VISIBLE);
                        view_3.setVisibility(View.VISIBLE);
                    }
                    break;
                case 9:
                    view_3.setVisibility(View.VISIBLE);
                    tv_3.setText("财务拒绝");
                    tv_2.setText("审核通过");
                    rl_wuliu.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    view_3.setBackgroundColor(Color.parseColor("#ee9821"));
                    tv_caiwu_title.setContentNotChange("审核失败");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    tv_3.setTextColor(Color.parseColor("#ee9821"));
                    tv_3.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont)), null);
                    break;
                case 10:
                    view_3.setBackgroundColor(Color.parseColor("#ee9821"));
                    tv_3.setText("财务通过");
                    tv_2.setText("审核通过");
                    tv_caiwu_tip.setVisibility(View.VISIBLE);
                    tv_caiwu_title.setContentNotChange("审核成功");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    tv_3.setTextColor(Color.parseColor("#ee9821"));
                    tv_3.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont)), null);
                    view_3.setVisibility(View.VISIBLE);
                    rl_wuliu.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    break;

            }
        } else {
            switch (data.orderStatus) {
                case 17:
                    tv_shenhe.setVisibility(View.VISIBLE);
                    tv_2.setText("正在审核");
                    tv_shenhe.setVisibility(View.VISIBLE);
                    tv_2.setTextColor(Color.parseColor("#666666"));
                    tv_2.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont2)), null);
                    break;
                case -1:
                    tv_2.setText("审核失败");
                    tv_shenhe.setContentNotChange("审核失败，详情请");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    tv_shenhe.setVisibility(View.VISIBLE);
                    break;
                case 18:
                    tv_2.setText("审核通过");
                    rl_back.setVisibility(View.VISIBLE);
                    tv_3.setText("财务审核");
                    tv_shenhe.setVisibility(View.VISIBLE);
                    tv_shenhe.setContentNotChange("审核成功");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    tv_caiwu_tip.setVisibility(View.VISIBLE);
                    view_3.setVisibility(View.VISIBLE);
                    tv_3.setTextColor(Color.parseColor("#666666"));
                    tv_3.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont)), null);
                    break;
                case 9:
                    view_3.setVisibility(View.VISIBLE);
                    tv_3.setText("财务拒绝");
                    tv_2.setText("审核通过");
                    tv_3.setTextColor(Color.parseColor("#ee9821"));
                    tv_3.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont)), null);
                    rl_back.setVisibility(View.VISIBLE);
                    view_3.setBackgroundColor(Color.parseColor("#ee9821"));
                    tv_caiwu_title.setContentNotChange("审核失败");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    break;
                case 10:
                    view_3.setBackgroundColor(Color.parseColor("#ee9821"));
                    tv_3.setText("财务通过");
                    tv_2.setText("审核通过");
                    tv_shenhe.setVisibility(View.VISIBLE);
                    tv_shenhe.setContentNotChange("审核成功");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    tv_caiwu_tip.setVisibility(View.VISIBLE);
                    tv_3.setTextColor(Color.parseColor("#ee9821"));
                    tv_3.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(R.mipmap.shangcheng_piont)), null);
                    tv_caiwu_title.setContentNotChange("审核成功");
                    tv_shenhe.setTextNotChange("咨询客服" + data.shopTel);
                    view_3.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private ArrayList<ChooseWLNameBean.Data> wuNames;


    @OnClick(R.id.et_wl_name)
    void chooseWLName() {
        if (wuNames == null) {
            new ApiRequest<ChooseWLNameBean>() {
                @Override
                protected Map<String, String> getMap() {
                    return null;
                }

                @Override
                protected String getUrl() {
                    return ApiConstant.EXPRESSLIST;
                }

                @Override
                protected String getMsg(int code) {
                    return "物流列表获取失败";
                }

                @Override
                protected Class<ChooseWLNameBean> getClx() {
                    return null;
                }
            }.setOnRequestListeren(new OnSingleRequestListener<ChooseWLNameBean>() {
                @Override
                public void succes(boolean isWrite, ChooseWLNameBean bean) {
                    wuNames = bean.getData();
                    getWLName();
                }

                @Override
                public void error(boolean isWrite, ChooseWLNameBean bean, String msg) {

                }
            }).post(new TipLoadingBean());
        } else {
            getWLName();
        }
    }

    private String expressId;
    private String expressNo;

    private void getWLName() {
        new ChooseStringView<ChooseWLNameBean.Data>(getContext(), wuNames) {
            @Override
            protected void itemClick(int position) {
                super.itemClick(position);
                et_wl_sn.setText(wuNames.get(position).expressName);
                expressId = wuNames.get(position).expressId;
                expressNo = wuNames.get(position).expressCode;
            }
        }.showAtLocation(false);
    }

    private int position;

    @OnClick(R.id.bt_wl_write)
    void writeWl() {

        if (TextUtils.isEmpty(expressId)) {
            MyToast.showToast("请填写物流名称");
            return;
        }
        final String sn = et_wl_sn.getText().toString();
        if (TextUtils.isEmpty(sn)) {
            MyToast.showToast("请填写物流单号");
            return;
        }

        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("orderId", data.orderId);
                map.put("expressId", expressId);
                map.put("expressNo", sn);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.TXEXPRESS;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }

            @Override
            protected String getMsg(int code) {
                return "提交物流信息失败";
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {

            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                RxBus.get().post("orderDescRxBus", "");
                RxBus.get().post("orderManager", position);
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean());
    }

}
