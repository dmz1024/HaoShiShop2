package haoshi.com.shop.fragment.reg;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

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
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.PhoneUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class WeChatAndQQRegFragment extends SingleNetWorkBaseFragment<LoginBean> implements OnTitleBarListener {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_affirm_password)
    EditText et_affirm_password;
    @BindView(R.id.bt_reg)
    Button bt_reg;

    public static WeChatAndQQRegFragment getInstance(ArrayList<String> data) {
        WeChatAndQQRegFragment fragment = new WeChatAndQQRegFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ArrayList<String> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            data = bundle.getStringArrayList("data");
        }
    }

    @Override
    protected String url() {
        return ApiConstant.REGS;
    }

    @Override
    protected Class<LoginBean> getTClass() {
        return LoginBean.class;
    }

    @Override
    protected Map<String, String> map() {
        map.put("iphone", name);
        map.put("openid", data.get(0));
        map.put("unionid", data.get(1));
        map.put("type", data.get(2));
        map.put("headimgurl", data.get(3));
        map.put("nickname", data.get(4));
        map.put("sex", data.get(5));
        map.put("loginPwd", password);
        return super.map();
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
        RxBus.get().post("addFragment", new AddFragmentBean(new PerfectRegChooseUserInfoFragment()));
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_wechat_and_qq_reg, null);
        ButterKnife.bind(this, view);
        et_name.setText(data.get(6));
        et_name.setEnabled(false);
        et_name.setFocusableInTouchMode(false);
        return view;
    }

    @Override
    protected String getnMsg(int code) {
        switch (code) {
            case 10001:
                return "请输入用户名";
            case 10003:
                return "账号或密码包含特殊字符";
            case 10004:
                return "该账号已注册";
            case 10006:
                return "注册信息不全";
            case 10007:
                return "验证码已过期";
            case 10008:
                return "验证码错误";
        }
        return super.getnMsg(code);
    }

    private String name;
    private String password;
    private String code;

    @OnClick(R.id.bt_reg)
    void reg() {
        name = "";
        password = "";
        code = "";
        name = et_name.getText().toString();
        password = et_password.getText().toString();
        if (!PhoneUtil.isTel(name)) {
            MyToast.showToast("手机号格式不正确");
            return;
        }

        if (!PhoneUtil.zhengze(password, Pattern.compile("^((?=.*[0-9].*)(?=.*[a-z].*))[_0-9a-z]{6,10}$"))) {
            MyToast.showToast("密码为数字和字母组合,且为6-16位");
            return;
        }

        if (!TextUtils.equals(password, et_affirm_password.getText().toString())) {
            MyToast.showToast("两次密码不一致");
            et_password.setText("");
            et_affirm_password.setText("");
            return;
        }
        getData();
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("新用户注册").setOnTitleBarListener(this);
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
        return new TipLoadingBean("正在注册", "", "");
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
