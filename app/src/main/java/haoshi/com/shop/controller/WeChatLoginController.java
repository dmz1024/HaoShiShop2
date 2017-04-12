package haoshi.com.shop.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.BaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2017/4/10.
 */

public class WeChatLoginController {
    public static WeChatLoginController getInstance() {
        return new WeChatLoginController();
    }


    /**
     * 通过code获取access_token
     *
     * @param code
     * @param listener
     */
    public void getAccessToken(final String code, OnSingleRequestListener<AccessToken> listener) {
        new ApiRequest<AccessToken>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("code", code);
                return map;
            }


            @Override
            protected String getUrl() {
                return ApiConstant.APPTOKEN;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "登录失败";
                    case 10011:
                        return "请先绑定手机号";
                }
                return super.getMsg(code);
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<AccessToken> getClx() {
                return AccessToken.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在获取微信信息", "", ""));
    }


    public static class AccessToken extends BaseBean<AccessToken.Data> {
        public static class Data implements Serializable {
            public String openid;
            public String unionid;
            public String nickname;
            public String sex;
            public String headimgurl;
            public int errcode;
        }

    }
}
