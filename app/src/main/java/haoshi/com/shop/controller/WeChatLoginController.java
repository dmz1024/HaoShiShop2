package haoshi.com.shop.controller;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.BaseBean;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.ContextUtil;
import view.pop.TipMessage;

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


    /**
     * 通过code获取access_token
     *
     * @param code
     * @param listener
     */
    public void getChatOponId(final String code, OnSingleRequestListener<AccessToken> listener) {
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


    /**
     * 解除微信或QQ绑定
     *
     * @param type
     * @param listener
     */
    public void relieveBind(final String type, final OnSingleRequestListener<SingleBaseBean> listener) {
        new TipMessage(ContextUtil.getCtx(), new TipMessage.TipMessageBean("提示", "确认解除" + (TextUtils.equals("wx", type) ? "微信绑定?" : "QQ绑定?"), "取消", "解绑")) {
            @Override
            protected void right() {
                super.right();
                new ApiRequest<SingleBaseBean>() {
                    @Override
                    protected Map<String, String> getMap() {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", UserInfo.userId);
                        map.put("token", UserInfo.token);
                        map.put("type", type);
                        return map;
                    }

                    @Override
                    protected boolean getShowSucces() {
                        return false;
                    }

                    @Override
                    protected String getUrl() {
                        return ApiConstant.UNBUNDLING;
                    }

                    @Override
                    protected Class<SingleBaseBean> getClx() {
                        return SingleBaseBean.class;
                    }
                }.setOnRequestListeren(listener).post(new TipLoadingBean());
            }
        }.showAtLocation(true);

    }


    /**
     * 微信或QQ绑定
     *
     * @param type
     * @param opon_id
     * @param listener
     */
    public void bind(final String type,final String opon_id, final OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("type", type);
                map.put("open_id", opon_id);
                return map;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.BINDTHIRDLOGIN;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean());

    }
}
