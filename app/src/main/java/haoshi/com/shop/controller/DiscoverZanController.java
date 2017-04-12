package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2017/3/19.
 */

public class DiscoverZanController {
    public static DiscoverZanController getInstance() {
        return new DiscoverZanController();
    }


    /**
     * 点赞
     * @param catId
     * @param title
     * @param listener
     */
    public void addCollect(final String catId, final String title,OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("goodsId", catId);
                map.put("goodsName", title);
                return map;
            }
            @Override
            protected String getUrl() {
                return ApiConstant.ISZAN;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }
            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post();

    }

    /**
     * 查看赞
     * @param id
     * @param listener
     */
    public void lookCollect(final String id,OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("zanuid", id);
                return map;
            }
            @Override
            protected String getUrl() {
                return ApiConstant.ISSEES;
            }


            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(listener).post();

    }


}
