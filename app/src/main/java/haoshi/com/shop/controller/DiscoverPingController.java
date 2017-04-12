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

public class DiscoverPingController {
    public static DiscoverPingController getInstance() {
        return new DiscoverPingController();
    }


    /**
     * 评论
     * @param catId
     * @param title
     * @param listener
     */
    public void addPing(final String catId, final String title,OnSingleRequestListener<SingleBaseBean> listener) {
//        new ApiRequest<SingleBaseBean>() {
//            @Override
//            protected Map<String, String> getMap() {
//                Map<String, String> map = new HashMap<>();
//                map.put("userId", UserInfo.userId);
//                map.put("token", UserInfo.token);
//                map.put("goodsId", goodsCatId);
//                map.put("goodsName", title);
//                return map;
//            }
//            @Override
//            protected String getUrl() {
//                return ApiConstant.ISZAN;
//            }
//
//            @Override
//            protected boolean getShowSucces() {
//                return false;
//            }
//            @Override
//            protected Class<SingleBaseBean> getClx() {
//                return SingleBaseBean.class;
//            }
//        }.setOnRequestListeren(listener).post();

    }

    /**
     * 查看评论
     * @param uid
     * @param articleId
     * @param listener
     */
    public void lookPing(final String uid,final String articleId,OnSingleRequestListener<SingleBaseBean> listener) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                map.put("zanuid", uid);
                map.put("goodsId", articleId);
                return map;
            }
            @Override
            protected String getUrl() {
                return ApiConstant.ISCOMMENTSEES;
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
