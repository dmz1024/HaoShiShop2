package haoshi.com.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import haoshi.com.shop.bean.login.LoginBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.fragment.login.BindingWechatAndQQFragment;
import interfaces.OnSingleRequestListener;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/4/10.
 */

public class LoginController {
    public static LoginController getInstance() {
        return new LoginController();
    }


    public void weChatLogin(OnSingleRequestListener<LoginBean> listener, final WeChatLoginController.AccessToken.Data data, final String type) {
        new ApiRequest<LoginBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("openid", data.openid);
                map.put("unionid", data.unionid);
                map.put("type", type);
                return map;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10011:
                        ArrayList<String> list = new ArrayList<>();
                        list.add(data.openid);
                        list.add(data.unionid);
                        list.add(type);
                        list.add(data.headimgurl);
                        list.add(data.nickname);
                        list.add(data.sex);
                        RxBus.get().post("addFragment", new AddFragmentBean(BindingWechatAndQQFragment.getInstance(list)));
                        return "请先绑定账号";
                }
                return super.getMsg(code);
            }

            @Override
            protected String getUrl() {
                return ApiConstant.WECHATLOGIN;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<LoginBean> getClx() {
                return LoginBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在登录...", "", "登录失败"));
    }
}
