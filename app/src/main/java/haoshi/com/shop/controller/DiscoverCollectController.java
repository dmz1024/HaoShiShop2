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
 * Created by dengmingzhi on 2017/3/19.
 */

public class DiscoverCollectController {
    public static DiscoverCollectController getInstance() {
        return new DiscoverCollectController();
    }


    public void addCollect(final String catId, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("goodsId", catId);
                return map;
            }



            @Override
            protected String getUrl() {
                return ApiConstant.ARLECOLLECTS;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "收藏失败";
                    case 10017:
                        return "已收藏过";
                }

                return super.getMsg(code);
            }
            @Override
            protected boolean getShowSucces() {
                return false;
            }
            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("正在收藏", "", "收藏失败"));

    }

    public void cancelCollect(final String catId, OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("goodsId", catId);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.BOOMJUICES;
            }

            @Override
            protected String getMsg(int code) {
                switch (code) {
                    case 10001:
                        return "取消失败";
                    case 10018:
                        return "商品不存在";
                }

                return super.getMsg(code);
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post(new TipLoadingBean("取消收藏", "", "取消失败"));

    }


}
