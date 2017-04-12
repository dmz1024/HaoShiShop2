package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2017/3/30.
 */

public class SendCodeController {
    public static SendCodeController getInstance() {
        return new SendCodeController();
    }

    /**
     * 获取注册验证码
     *
     * @param tel
     */
    public void getRegCode(final String tel, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", tel);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.GETPHONEVERIFYCODE;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "手机号格式不正确";
                    case 10002:
                        return "该手机已注册";
                    case 10003:
                        return "获取验证码次数超过规定数";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在获取短信验证码", "验证码已发送，请注意查收", "验证码发送失败"));
    }

    /**
     * 获取注册验证码
     *
     * @param tel
     */
    public void getupPwdCode(final String tel, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", tel);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.UPPWDVERIFYCODE;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "手机号格式不正确";
                    case 10002:
                        return "该手机还未注册";
                    case 10003:
                        return "获取验证码次数超过规定数";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在获取短信验证码", "验证码已发送，请注意查收", "验证码发送失败"));
    }

    /**
     * 绑定验证码
     *
     * @param tel
     */
    public void getBindingCode(final String tel, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", tel);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.WXCHATVERIFYCODE;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "手机号格式不正确";
                    case 10002:
                        return "该手机还未注册";
                    case 10003:
                        return "获取验证码次数超过规定数";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在获取短信验证码", "验证码已发送，请注意查收", "验证码发送失败"));
    }




}
