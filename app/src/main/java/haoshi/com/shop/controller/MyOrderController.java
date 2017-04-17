package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.bean.shop.OrderIdBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/23.
 */

public class MyOrderController {
    public static MyOrderController getInstance() {
        return new MyOrderController();
    }


    public void submitOrder(final String addressId, final String note, OnSingleRequestListener<OrderIdBean> listener) {
        new ApiRequest<OrderIdBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("addressId", addressId);
                map.put("remark", note);
                return map;
            }


            @Override
            protected String getUrl() {
                return ApiConstant.SUBMIT_ORDER;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10002:
                        return "请检查地址是否正确";
                    case 10001:
                        RxBus.get().post("back", "back");
                        return "请勿重复提交订单,可去个人中心查看生成的订单";
                }
                return super.getMsg(code);
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<OrderIdBean> getClx() {
                return OrderIdBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("提交订单", "提交成功", "提交失败"));
    }


    /**
     * 取消订单
     *
     * @param id
     * @param listener
     */
    public void cancelOrder(final String id, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("orderId", id);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.CANCELREFUNDS;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "取消失败";
                    case 10009:
                        return "该订单状态已改变，取消失败,请刷新重试";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在取消订单", "取消成功", "取消失败"));
    }


    /**
     * 删除订单
     *
     * @param id
     * @param listener
     */
    public void deleteOrder(final String id, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("orderId", id);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.DELORDERS;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "删除失败";
                    case 10009:
                        return "该订单状态已改变，删除失败,请刷新重试";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在删除订单", "", "删除失败"));
    }

    /**
     * 取消退款
     *
     * @param orderId
     */
    public void cancelRefunds(final String orderId, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("orderId", orderId);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.CANCELREFUNDS;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "取消失败";
                    case 10009:
                        return "该订单状态已改变，取消失败,请刷新重试";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在取消退款", "", "取消失败"));
    }

    /**
     * 确认收货
     *
     * @param orderId
     */
    public void receives(final String orderId, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("orderId", orderId);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.RECEIVES;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "确认收货失败";
                    case 10009:
                        return "该订单状态已改变，确认失败,请刷新重试";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在确认收货", "", "收货失败"));
    }
}
