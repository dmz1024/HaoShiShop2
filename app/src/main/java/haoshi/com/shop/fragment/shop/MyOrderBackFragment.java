package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.FileBinary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ApiRequest;
import api.UpLoadRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.UpLoadBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChoosePhotoAdapter;
import haoshi.com.shop.adapter.MyOrderGoodAdapter;
import haoshi.com.shop.bean.shop.BackOrderCauseBean;
import haoshi.com.shop.bean.shop.MyOrderBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;
import view.pop.PopEdit;
import view.pop.TipLoading;

/**
 * Created by dengmingzhi on 2017/4/17.
 */

public class MyOrderBackFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    @BindView(R.id.tv_return_type)
    TextView tv_return_type;
    @BindView(R.id.tv_back_desc)
    TextView tv_back_desc;
    @BindView(R.id.tv_max_price)
    TextView tv_max_price;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.rl_photo)
    RecyclerView rl_photo;
    @BindView(R.id.rl_shop)
    RecyclerView rl_shop;
    @BindView(R.id.tv_total_money)
    TextView tv_total_money;

    @Override
    protected String url() {
        return ApiConstant.REFUND;
    }

    public static MyOrderBackFragment getInstance(ArrayList<MyOrderBean.Data.OrderGoodBean> goods, int type, double money, String id, int position) {
        MyOrderBackFragment fragment = new MyOrderBackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("goods", goods);
        bundle.putInt("type", type);
        bundle.putString("id", id);
        bundle.putDouble("money", money);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ArrayList<MyOrderBean.Data.OrderGoodBean> goods;
    private int type;
    private double money;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            goods = bundle.getParcelableArrayList("goods");
            type = bundle.getInt("type");
            money = bundle.getDouble("money");
            id = bundle.getString("id");
            position = bundle.getInt("position");
        }
    }

    private int position;

    private String id;

    private String content;

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("orderId", id);
        map.put("reason", cause_id);
        map.put("money", backMoney + "");
        map.put("type", type + "");
        map.put("refundExplain", backDesc);
        if (!TextUtils.isEmpty(voucherImg)) {
            map.put("voucherImg", voucherImg);
        }
        if (!TextUtils.isEmpty(content)) {
            map.put("content", "其他原因");
        }

        return super.map();
    }


    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        RxBus.get().post("orderDescRxBus", "");
        RxBus.get().post("orderManager", position);
        RxBus.get().post("back", "back");

    }

    @Override
    protected boolean showSucces() {
        return false;
    }

    @Override
    protected String getnMsg(int code) {
        switch (code) {
            case 10001:
                return "申请失败";
            case 10007:
                return "一个订单只能申请一次退款货";
            case 10008:
                return "输入金额不能大于实付金额";
            case 10009:
                return "订单状态已改变";
            case 10010:
                return "退款金额不能为0";
            case 10011:
                return "还未收货不能退货";
        }
        return super.getnMsg(code);
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent(type == 0 ? "退款" : "退货")
                .setOnTitleBarListener(this);
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    private ChoosePhotoAdapter choosePhotoAdapter;

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_order_back, null);
        ButterKnife.bind(this, view);
        choosePhotoAdapter = new ChoosePhotoAdapter(getContext(), photos).setIndex(1005).setMax(3);
        rl_photo.setAdapter(choosePhotoAdapter);
        rl_photo.setLayoutManager(new GridLayoutManager(getContext(), 3));
        et_price.setText(money + "");
        tv_total_money.setText("合计金额：￥" + money);
        tv_max_price.setText("(最多" + money + "元)");
        rl_shop.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rl_shop.setAdapter(new MyOrderGoodAdapter(getContext(), goods));

        return view;
    }

    private ArrayList<BackOrderCauseBean.Data> cause;
    private String cause_id;

    @OnClick(R.id.tv_return_type)
    void returnType() {
        if (cause == null) {
            new ApiRequest<BackOrderCauseBean>() {
                @Override
                protected Map<String, String> getMap() {
                    return null;
                }

                @Override
                protected boolean getShowSucces() {
                    return false;
                }

                @Override
                protected String getUrl() {
                    return ApiConstant.REASON;
                }

                @Override
                protected Class<BackOrderCauseBean> getClx() {
                    return BackOrderCauseBean.class;
                }
            }.setOnRequestListeren(new OnSingleRequestListener<BackOrderCauseBean>() {
                @Override
                public void succes(boolean isWrite, BackOrderCauseBean bean) {
                    cause = bean.getData();
                    showCause();
                }

                @Override
                public void error(boolean isWrite, BackOrderCauseBean bean, String msg) {

                }
            }).post(new TipLoadingBean());
        } else {
            showCause();
        }
    }

    private void showCause() {
        new ChooseStringView<BackOrderCauseBean.Data>(getContext(), cause) {
            @Override
            protected void itemClick(int position) {
                super.itemClick(position);
                if (TextUtils.equals("其他", cause.get(position).getString())) {
                    content = "其他";
                } else {
                    content = "";
                }
                cause_id = cause.get(position).ID;
                tv_return_type.setText("退款原因       " + cause.get(position).getString());
            }
        }.showAtLocation(false);
    }

    @OnClick(R.id.tv_back_desc)
    void backDesc() {
        new PopEdit(getContext(), tv_back_desc.getText().toString()) {
            @Override
            protected void content(String content) {
                super.content(content);
                tv_back_desc.setText(content);
            }
        }.showAtLocation(false);
    }

    private String backDesc;
    private double backMoney;

    @OnClick(R.id.bt_submit)
    void submit() {
        if (TextUtils.isEmpty(cause_id)) {
            MyToast.showToast("请输入退款原因");
            return;
        }

        String price = et_price.getText().toString();
        if (TextUtils.isEmpty(price)) {
            MyToast.showToast("请输入退款金额");
            return;
        }

        try {
            backMoney = Double.parseDouble(price);
        } catch (Exception e) {
            MyToast.showToast("请输入正确格式的退款金额");
            return;
        }
        if (backMoney <= 0) {
            MyToast.showToast("退款金额不能小于等于0");
            return;
        }


        if (backMoney > money) {
            MyToast.showToast("退款金额不能大于" + money);
            return;
        }

        backDesc = tv_back_desc.getText().toString();
        if (TextUtils.isEmpty(backDesc)) {
            MyToast.showToast("请输入退款详情");
            return;
        }

        if (photos.size() > 0) {
            loadPhotos();
        } else {
            getData();
        }
    }

    private String voucherImg;

    private void loadPhotos() {
        new UpLoadRequest<UpLoadBean>() {
            @Override
            protected List<Binary> getFiles() {
                List<Binary> binaries = new ArrayList<>();
                for (String p : photos) {
                    binaries.add(new FileBinary(new File(p)));
                }
                return binaries;
            }

            @Override
            protected Class<UpLoadBean> getClx() {
                return UpLoadBean.class;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.BACKIMG;
            }
        }.request(new OnSingleRequestListener<UpLoadBean>() {
            @Override
            public void succes(boolean isWrite, UpLoadBean bean) {

                final StringBuilder sb = new StringBuilder();
                for (String s : bean.data) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(s);
                }
                voucherImg = sb.toString();
                getData();

            }

            @Override
            public void error(boolean isWrite, UpLoadBean bean, String msg) {
                MyToast.showToast("图片上传失败");
            }
        }, new TipLoading(getContext()));
    }


    private ArrayList<String> photos = new ArrayList<>();

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (choosePhotoAdapter != null) {
            choosePhotoAdapter.onDestroy();
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
}
