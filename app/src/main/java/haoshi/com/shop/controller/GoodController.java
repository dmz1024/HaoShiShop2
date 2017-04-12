package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2017/3/24.
 */

public class GoodController {
    public static GoodController getInstance() {
        return new GoodController();
    }


    public void addCollect(final String id, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("goodsId", id);
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                return map;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "收藏失败";
                    case 10017:
                        return "您已收藏过该商品";
                }
                return super.getMsg(code);
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADD_GOOD_COLLECTIONS;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener)
                .post(new TipLoadingBean("正在添加收藏", "收藏成功", "收藏失败"));
    }

    public void cancelCollect(final String id, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("goodsId", id);
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                return map;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "收藏失败";
                }
                return super.getMsg(code);
            }

            @Override
            protected String getUrl() {
                return ApiConstant.CANCELCOLLECTIONS;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener)
                .post(new TipLoadingBean("正在取消收藏", "取消成功", "取消失败"));
    }


}
