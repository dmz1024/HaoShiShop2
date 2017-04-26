package haoshi.com.shop.fragment.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import base.bean.ChooseStringBean;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.activity.MainActivity;
import haoshi.com.shop.bean.login.LoginBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.controller.LoginController;
import haoshi.com.shop.controller.WeChatLoginController;
import haoshi.com.shop.fragment.index.IndexFragment;
import haoshi.com.shop.fragment.reg.PerfectRegChooseUserInfoFragment;
import haoshi.com.shop.fragment.reg.RegFragment;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.PhoneUtil;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.ChooseStringView;
import view.pop.TipMessage;

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

    @Override
    protected String getnMsg(int code) {
        switch (code) {
            case 10009:
                return "用户不存在";
            case 10010:
                return "密码错误";
        }
        return super.getnMsg(code);
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("登录")
                .showVisiLeft(View.GONE)
                .setRightContent("新用户注册")
                .setRightColor("#c3c3c3")
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
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
        if (!PhoneUtil.isTel(name)) {
            MyToast.showToast("手机号格式不正确");
            return;
        }

        if (!PhoneUtil.zhengze(password, Pattern.compile("^((?=.*[0-9].*)(?=.*[a-z].*))[_0-9a-z]{6,10}$"))) {
            MyToast.showToast("密码为数字和字母组合,且为6-16位");
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

    private IWXAPI weChatApi;

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("regLoginRxBus", regLoginRxBus);
        RxBus.get().unregister("clearBroadCast", clearBroadCast);
    }

    @Override
    protected boolean getDis() {
        return true;
    }


    @OnClick(R.id.iv_wechat)
    void wechat() {
        if (weChatApi == null) {
            weChatApi = WXAPIFactory.createWXAPI(getContext(), WECHAT_ID, true);
        }

        if (!weChatApi.isWXAppInstalled()) {
            MyToast.showToast("请先下载微信客户端");
            return;
        }

        initClearBroadCast();
        if (loginBroadcastReceiver == null) {
            loginBroadcastReceiver = new LoginBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("login_receiver");
            getContext().registerReceiver(loginBroadcastReceiver, filter);
        }

        weChatApi.registerApp(WECHAT_ID);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        weChatApi.sendReq(req);
    }


    private Tencent tencent;

    @OnClick(R.id.iv_qq)
    void qq() {
        if (tencent == null) {
            tencent = Tencent.createInstance(QQ_ID, getContext().getApplicationContext());
        }

        if (!tencent.isSessionValid()) {
            isQq = true;
            tencent.login(this, "all", new LoginUilister());
        }
    }

    private boolean isQq;

    private class LoginUilister implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.d("qq登录", o.toString());
            if (isQq) {
                isQq = false;
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    String openID = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    tencent.setOpenId(openID);
                    tencent.setAccessToken(token, expires);
                    com.tencent.connect.UserInfo userInfo = new com.tencent.connect.UserInfo(getContext(), tencent.getQQToken());
                    userInfo.getUserInfo(new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            try {
                                JSONObject jb = new JSONObject(o.toString());
                                WeChatLoginController.AccessToken.Data data = new WeChatLoginController.AccessToken.Data();
                                data.openid = tencent.getOpenId();
                                data.nickname = jb.getString("nickname");
                                data.sex = jb.getString("gender");
                                data.headimgurl = jb.getString("figureurl_qq_2");
                                data.unionid = "";
                                LoginController.getInstance().weChatLogin(new OnSingleRequestListener<LoginBean>() {
                                    @Override
                                    public void succes(boolean isWrite, LoginBean bean) {
                                        UserInfo.saveUserInfo(bean);
                                        RxBus.get().post("clearAll", "");
                                    }

                                    @Override
                                    public void error(boolean isWrite, LoginBean bean, String msg) {

                                    }
                                }, data, "qq");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(UiError uiError) {
                            MyToast.showToast("获取用户信息失败");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void onError(UiError uiError) {
            Log.d("qq登录", uiError.toString());
        }

        @Override
        public void onCancel() {
            Log.d("qq登录", "取消");
        }
    }


    private static final String QQ_ID = "1106008551";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (tencent != null) {
            tencent.handleLoginData(data, new LoginUilister());
        }
    }

    private LoginBroadcastReceiver loginBroadcastReceiver;

    public class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "login_receiver")) {
                String sdk = intent.getStringExtra("sdk");
                if (TextUtils.equals("wechat", sdk)) {
                    String code = intent.getStringExtra("code");
                    Log.d("code", code);
                    WeChatLoginController.getInstance().getAccessToken(code, new OnSingleRequestListener<WeChatLoginController.AccessToken>() {
                        @Override
                        public void succes(boolean isWrite, WeChatLoginController.AccessToken bean) {
                            if (bean.getData().errcode == 0) {
                                LoginController.getInstance().weChatLogin(new OnSingleRequestListener<LoginBean>() {
                                    @Override
                                    public void succes(boolean isWrite, LoginBean bean) {
                                        UserInfo.saveUserInfo(bean);
                                        RxBus.get().post("clearAll", "");

                                    }

                                    @Override
                                    public void error(boolean isWrite, LoginBean bean, String msg) {
                                    }
                                }, bean.getData(), "wx");
                            } else {
                                MyToast.showToast("第三方登录失败,请重试");
                            }
                        }

                        @Override
                        public void error(boolean isWrite, WeChatLoginController.AccessToken bean, String msg) {

                        }
                    });
                }
            }
        }
    }


    private static final String WECHAT_ID = "wx2c2dcafaa34cf74e";

    private Observable<String> clearBroadCast;

    private void initClearBroadCast() {
        if (clearBroadCast == null) {
            clearBroadCast = RxBus.get().register("clearBroadCast", String.class);
            clearBroadCast.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    loginBroadcastReceiver = null;
                }
            });
        }

    }



}
