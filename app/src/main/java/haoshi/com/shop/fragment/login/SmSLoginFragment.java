package haoshi.com.shop.fragment.login;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import haoshi.com.shop.fragment.reg.PerfectRegChooseUserInfoFragment;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.PhoneUtil;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.TipMessage;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class SmSLoginFragment extends SingleNetWorkBaseFragment<LoginBean> implements OnTitleBarListener {
    private String name;
    private String code;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_send)
    TextView tv_send;

    @Override
    protected String url() {
        return ApiConstant.SMS_LOGIN;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userPhone", name);
        map.put("code", code);
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
        return new TipLoadingBean("正在登录", "", "登录失败");
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
        UserInfo.saveUserInfo(bean);
        if (TextUtils.equals("0", UserInfo.isThree)) {
            new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "您的信息未完善，请先完善信息", "", "去完善")) {
                @Override
                protected void right() {
                    super.right();
                    RxBus.get().post("addFragment", new AddFragmentBean(new PerfectRegChooseUserInfoFragment()));
                }
            }.showAtLocation(true);
        } else {
            RxBus.get().post("clearAll", "");
        }
    }

    @OnClick(R.id.bt_login)
    void login() {
        name = "";
        code = "";
        name = et_name.getText().toString();
        code = et_code.getText().toString();
        if (!PhoneUtil.isTel(name)) {
            MyToast.showToast("手机号格式不正确");
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
        View view = View.inflate(getContext(), R.layout.fragment_sms_login, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.tv_send)
    void send() {

        name = et_name.getText().toString();
        if (!PhoneUtil.isTel(name)) {
            MyToast.showToast("手机号格式不正确");
            return;
        }
        SendCodeController.getInstance().getupPwdCode(name, new OnSingleRequestListener<SingleBaseBean>() {
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("短信登录").setOnTitleBarListener(this);
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
