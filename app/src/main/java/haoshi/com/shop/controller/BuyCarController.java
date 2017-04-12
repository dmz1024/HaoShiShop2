package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.bean.shop.AddCarBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2017/3/21.
 */

public class BuyCarController {
    public static BuyCarController getInstance() {
        return new BuyCarController();
    }

    public void addBuyCar(final boolean showSucces, OnSingleRequestListener<AddCarBean> listener, final String... str) {
        new ApiRequest<AddCarBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("goodsId", str[0]);
                map.put("sid", str[1]);
                map.put("buyNum", str[2]);
                return map;
            }

            @Override
            protected boolean getShowSucces() {
                return showSucces;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADDCART;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10005:
                        return "库存不足";
                }
                return super.getMsg(code);
            }

            @Override
            protected Class<AddCarBean> getClx() {
                return AddCarBean.class;
            }
        }.setOnRequestListeren(listener)
                .post(showSucces ? new TipLoadingBean("添加至购物车", "添加成功", "添加失败") : new TipLoadingBean());

    }


    public void deleteBuyCar(OnSingleRequestListener<SingleBaseBean> listener, final String id) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("cartId", id);
                return map;
            }


            @Override
            protected String getUrl() {
                return ApiConstant.DELCART;
            }


            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener)
                .post(new TipLoadingBean("删除购物车商品", "删除成功", "删除失败"));

    }
}
