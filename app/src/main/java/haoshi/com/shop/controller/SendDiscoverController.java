package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.constant.ApiConstant;
import interfaces.OnSingleRequestListener;

/**
 * Created by dengmingzhi on 2017/3/24.
 */

public class SendDiscoverController {
    public static SendDiscoverController getInstance() {
        return new SendDiscoverController();
    }

    public void sendDiscover(final String attr, OnSingleRequestListener<SingleBaseBean> listener, boolean isShow, final String... g) {
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("goodsName", g[0]);
                map.put("goodsCatId", g[1]);
                map.put("userId", g[2]);
                map.put("token", g[3]);
                if (g.length == 5) {
                    map.put("gallery", g[4]);
                }
                if (attr.length() > 0) {
                    String[] at = attr.split("&&&");
                    for (String a : at) {
                        map.put(a.split("&&")[0], a.split("&&")[1]);
                    }
                }
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.NEWDYNAMICS;
            }

            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }
                .setOnRequestListeren(listener).
                post(isShow ? new TipLoadingBean("正在发布动态", "发布成功", "发布失败") : null);
    }
}
