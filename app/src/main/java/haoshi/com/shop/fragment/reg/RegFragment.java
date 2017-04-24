package haoshi.com.shop.fragment.reg;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class RegFragment extends SingleNetWorkBaseFragment<LoginBean> implements OnTitleBarListener {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_affirm_password)
    EditText et_affirm_password;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.bt_reg)
    Button bt_reg;

    @Override
    protected String url() {
        return ApiConstant.REG;
    }

    @Override
    protected Class<LoginBean> getTClass() {
        return LoginBean.class;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userPhone", name);
        map.put("loginPwd", password);
        map.put("code", code);
        return super.map();
    }

    @Override
    protected void writeData(boolean isWrite, LoginBean bean) {
        super.writeData(isWrite, bean);
        UserInfo.saveUserInfo(bean);
        RxBus.get().post("back","back");
        RxBus.get().post("addFragment",new AddFragmentBean(new PerfectRegChooseUserInfoFragment()));
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_reg, null);
        ButterKnife.bind(this, view);
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
        code = et_code.getText().toString();
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


        if (TextUtils.isEmpty(code)) {
            MyToast.showToast("请输入验证码");
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

    @OnClick(R.id.tv_send)
    void getCode() {
        name = et_name.getText().toString();
        if (name.length() != 11) {
            MyToast.showToast("请输入手机号");
            return;
        }

        SendCodeController.getInstance().getRegCode(name, new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                tv_send.setEnabled(false);
                timer.cancel();
                timer.start();
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            tv_send.setText("重新获取(" + (l / 1000) + ")S");
        }

        @Override
        public void onFinish() {
            tv_send.setEnabled(true);
            tv_send.setText("发送验证码");
        }
    };
}
