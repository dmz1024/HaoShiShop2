package client.fragment.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Map;

import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.NetworkBaseFragment;
import base.fragment.NotNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.R;
import client.bean.login.LoginBean;
import client.constant.ApiConstant;
import client.constant.UserInfo;
import client.fragment.index.IndexFragment;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class SmSLoginFragment extends SingleNetWorkBaseFragment<LoginBean> {
    private String name;
    private String code;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_code)
    EditText et_code;

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
        return new TipLoadingBean("正在登录", "", "");
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
        RxBus.get().post("addFragment", new AddFragmentBean(new IndexFragment()));
    }

    @OnClick(R.id.bt_login)
    void login() {
        name = "";
        code = "";
        name = et_name.getText().toString();
        code = et_code.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToast.showToast("请输入账号");
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


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("短信登录");
    }


}
