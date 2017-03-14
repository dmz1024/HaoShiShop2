package client.fragment.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Map;

import base.bean.ChooseStringBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
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
import client.fragment.reg.PerfectRegChooseUserInfoFragment;
import client.fragment.reg.RegFragment;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;
import util.SharedPreferenUtil;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class LoginFragment extends SingleNetWorkBaseFragment<LoginBean> implements OnTitleBarListener {
    private String name;
    private String password;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.bt_login)
    Button bt_login;

    @Override
    protected String url() {
        return ApiConstant.LOGIN;
    }

    @Override
    protected Map<String, String> map() {
        map.put("loginName", name);
        map.put("loginPwd", password);
        return super.map();
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
    protected Class<LoginBean> getTClass() {
        return LoginBean.class;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_login, null);
        ButterKnife.bind(this, view);
        return view;
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
    protected void writeData(boolean isWrite, LoginBean bean) {
        super.writeData(isWrite, bean);
        UserInfo.saveUserInfo(bean);
        RxBus.get().post("addFragment", new AddFragmentBean(new IndexFragment()));
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("登录")
                .setRightContent("新用户注册")
                .setRightColor("#c3c3c3")
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {
        //TODO 注册
        initRegLoginRxBus();
        RxBus.get().post("addFragment", new AddFragmentBean(new RegFragment()));
    }

    @Override
    public void center() {

    }

    @OnClick(R.id.bt_login)
    void login() {
        name = "";
        password = "";
        name = et_name.getText().toString();
        password = et_password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToast.showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            MyToast.showToast("请输入密码");
            return;
        }
        getData();
    }

    @OnClick(R.id.tv_forgot)
    void forgot() {
        ArrayList<ChooseStringBean> chooses = new ArrayList<>();
        chooses.add(new ChooseStringBean("重置密码"));
        chooses.add(new ChooseStringBean("用手机短信登录"));
        new ChooseStringView<ChooseStringBean>(getContext(), chooses) {
            @Override
            protected void itemClick(int position) {
                switch (position) {
                    case 0:
                        RxBus.get().post("addFragment", new AddFragmentBean(new ForgotPasswordTelFragment()));
                        break;
                    case 1:
                        RxBus.get().post("addFragment", new AddFragmentBean(new SmSLoginFragment()));
                        break;
                }

            }
        }.showAtLocation(false);
    }

    private Observable<String[]> regLoginRxBus;

    private void initRegLoginRxBus() {
        if (regLoginRxBus == null) {
            regLoginRxBus = RxBus.get().register("regLoginRxBus", String[].class);
            regLoginRxBus.subscribe(new Action1<String[]>() {
                @Override
                public void call(String[] s) {
                    name = s[0];
                    password = s[1];
                    getData();
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("regLoginRxBus", regLoginRxBus);
    }

    @Override
    protected boolean getDis() {
        return true;
    }
}
