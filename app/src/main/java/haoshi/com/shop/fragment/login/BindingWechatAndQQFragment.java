package haoshi.com.shop.fragment.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.login.LoginBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.SendCodeController;
import haoshi.com.shop.fragment.index.IndexFragment;
import haoshi.com.shop.fragment.reg.WeChatAndQQRegFragment;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class BindingWechatAndQQFragment extends SingleNetWorkBaseFragment<LoginBean> implements OnTitleBarListener {
    private String name;
    private String code;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_send)
    TextView tv_send;

    private ArrayList<String> data;

    public static BindingWechatAndQQFragment getInstance(ArrayList<String> data) {
        BindingWechatAndQQFragment fragment = new BindingWechatAndQQFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        data = bundle.getStringArrayList("data");
    }

    @Override
    protected String url() {
        return ApiConstant.CHATCODES;
    }

    @Override
    protected Map<String, String> map() {
        map.put("openid", data.get(0));
        map.put("unionid", data.get(1));
        map.put("code", code);
        map.put("userPhone", name);
        map.put("type", data.get(2));
        return super.map();
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("正在绑定账号", "", "绑定失败");
    }

    @Override
    protected void manageError(boolean isWrite, LoginBean loginBean, String msg) {
        super.manageError(isWrite, loginBean, msg);
        switch (loginBean.code) {
            case 10009:
                MyToast.showToast("用户不存在");
                break;
            case 10010:
                MyToast.showToast("密码错误");
                break;
        }
    }

    @Override
    protected void writeData(boolean isWrite, LoginBean bean) {
        super.writeData(isWrite, bean);
        UserInfo.token = bean.data.token;
        UserInfo.userId = bean.data.userId;
        UserInfo.userName = bean.data.userName;
        UserInfo.userPhone = bean.data.userPhone;
        RxBus.get().post("back", "back");
        RxBus.get().post("back", "back");
        RxBus.get().post("addFragment", new AddFragmentBean(new IndexFragment()));
    }

    @OnClick(R.id.bt_login)
    void login() {
        name = "";
        code = "";
        name = et_name.getText().toString();
        code = et_code.getText().toString();
        if (name.length() != 11) {
            MyToast.showToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            MyToast.showToast("请输入验证码");
            return;
        }
        getData();
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected Class<LoginBean> getTClass() {
        return LoginBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_bindding_login, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.tv_send)
    void send() {

        name = et_name.getText().toString();
        if (name.length() != 11) {
            MyToast.showToast("请输入正确的手机号");
            return;
        }
        SendCodeController.getInstance().getBindingCode(name, new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                tv_send.setEnabled(false);
                timer.start();
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        });
    }


    @Override
    protected String getnMsg(int code) {
        switch (code) {
            case 10001:
                data.add(name);
                RxBus.get().post("addFragment", new AddFragmentBean(WeChatAndQQRegFragment.getInstance(data)));
                return "请先注册";
            case 10002:
                return "该手机号已绑定其他" + (TextUtils.equals(data.get(2), "wx") ? "微信" : "QQ");
            case 10007:
                return "验证码已过期";
            case 10008:
                return "验证码错误";
        }
        return super.getnMsg(code);
    }

    private CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setText("重新发送(" + (millisUntilFinished / 1000) + "S)");
        }

        @Override
        public void onFinish() {
            tv_send.setEnabled(true);
            tv_send.setText("获取验证码");
        }
    };


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("绑定账号").setOnTitleBarListener(this);
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
