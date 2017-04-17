package haoshi.com.shop.fragment.login;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import haoshi.com.shop.controller.SendCodeController;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class ForgotPasswordFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {
    private String tel;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_affirm_password)
    EditText et_affirm_password;
    @BindView(R.id.et_code)
    EditText et_code;

    public static ForgotPasswordFragment getInstance(String tel) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tel", tel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tel = bundle.getString("tel");
        }
    }


    @Override
    protected Map<String, String> map() {
        map.put("userPhone", tel);
        map.put("loginPwd", password);
        map.put("code", code);
        return super.map();
    }

    private String password;
    private String code;

    @Override
    protected String url() {
        return ApiConstant.UPPWD;
    }

    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_forgot_password, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void writeData(boolean isWrite, SingleBaseBean bean) {
        super.writeData(isWrite, bean);
        RxBus.get().post("back","back");
    }

    @OnClick(R.id.tv_send)
    void send() {
        SendCodeController.getInstance().getupPwdCode(tel, new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                initCodeTime();
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        });
    }


    private void initCodeTime() {
        tv_send.setEnabled(false);
        timer.start();
    }

    @Override
    protected boolean showSucces() {
        return true;
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("忘记密码").setOnTitleBarListener(this);
    }

    @OnClick(R.id.bt_affirm)
    void affirm() {
        code = et_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            MyToast.showToast("请输入验证码");
            return;
        }

        password = et_password.getText().toString();
        if (password.length() < 6) {
            MyToast.showToast("密码小于6位");
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
    protected TipLoadingBean getTipLoadingBean() {
        return new TipLoadingBean("正在重置密码", "重置成功", "重置密码失败");
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
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
