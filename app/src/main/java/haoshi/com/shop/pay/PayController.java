package haoshi.com.shop.pay;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.TipLoadingBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2016/11/29.
 */

public class PayController {

    public static PayController getInstance() {
        return new PayController();
    }


    /**
     * 获取微信支付信息
     *
     * @param id
     */
    public void wechat(final String id) {
        new ApiRequest<PayUtil.WechatInfo>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("order_id", id);
                return map;
            }

            @Override
            protected String getUrl() {

                return null;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<PayUtil.WechatInfo> getClx() {
                return PayUtil.WechatInfo.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<PayUtil.WechatInfo>() {

            @Override
            public void succes(boolean isWrite, PayUtil.WechatInfo bean) {
                PayUtil.getInstance().weChatPay(bean.data);
            }

            @Override
            public void error(boolean isWrite, PayUtil.WechatInfo bean, String msg) {

            }
        }).post(new TipLoadingBean("获取支付信息", "", "支付失败，网络可能会有延时，可等待后重试"));
    }

    /**
     * 获取支付宝支付信息
     *
     * @param id
     */
    public void ali(final String id) {
        new ApiRequest<PayUtil.AliPayInfo>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("orderid", id);
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                return map;
            }

            @Override
            protected String getUrl() {

                return ApiConstant.GETALIPAYSAPP;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10002:
                        return "该订单已支付";
                }
                return super.getMsg(code);
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }


            @Override
            protected Class<PayUtil.AliPayInfo> getClx() {
                return PayUtil.AliPayInfo.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<PayUtil.AliPayInfo>() {

            @Override
            public void succes(boolean isWrite, PayUtil.AliPayInfo bean) {
                PayUtil.getInstance().aliPay(bean.data);
            }

            @Override
            public void error(boolean isWrite, PayUtil.AliPayInfo bean, String msg) {

            }


        }).post(new TipLoadingBean("获取支付信息", "", "支付失败，网络可能会有延时，可等待后重试"));
    }
}
