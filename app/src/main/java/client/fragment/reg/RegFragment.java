package client.fragment.reg;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import base.bean.TipLoadingBean;
import base.fragment.NetworkBaseFragment;
import base.fragment.NotNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.R;
import client.bean.login.LoginBean;
import client.bean.login.RegBean;
import client.constant.ApiConstant;
import client.constant.UserInfo;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class RegFragment extends SingleNetWorkBaseFragment<LoginBean> {
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
        UserInfo.token = bean.data.token;
        UserInfo.userId = bean.data.userId;
        UserInfo.userName = bean.data.userName;
        UserInfo.userPhone = bean.data.userPhone;
        RxBus.get().post("regLoginRxBus", new String[]{name, password});
        RxBus.get().post("back", "back");

    }

    @Override
    protected void manageError(boolean isWrite, LoginBean regBean, String msg) {
        super.manageError(isWrite, regBean, msg);
        switch (regBean.code) {
            case 10001:
                MyToast.showToast("请输入用户名");
                break;
            case 10003:
                MyToast.showToast("账号或密码包含特殊字符");
                break;
            case 10004:
                MyToast.showToast("该账号已注册");
                break;
            case 10006:
                MyToast.showToast("注册信息不全");
                break;
            case 10007:
                MyToast.showToast("验证码已过期");
                break;
            case 10008:
                MyToast.showToast("验证码错误");
                break;
        }
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
        if (TextUtils.isEmpty(name)) {
            MyToast.showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            MyToast.showToast("请输入密码");
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("新用户注册");
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
    protected boolean getDis() {
        return true;
    }
}
