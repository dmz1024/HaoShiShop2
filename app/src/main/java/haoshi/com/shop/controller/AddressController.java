package haoshi.com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.SingleBaseBean;
import base.bean.TipLoadingBean;
import haoshi.com.shop.bean.rxbus.AddressRxBus;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnSingleRequestListener;
import util.RxBus;

/**
 * Created by dengmingzhi on 2016/12/23.
 */

public class AddressController {
    public static AddressController getInstance() {
        return new AddressController();
    }

    public void addOrUpdate(final Map<String, String> map) {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADDRESS_ADDS;
            }


            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                RxBus.get().post("address", new AddressRxBus("add_update"));
                RxBus.get().post("back", "back");
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean("保存地址", "保存成功", ""));
    }

    public void setDef(final AddressRxBus rxBus) {
        final Map<String, String> map = new HashMap<>();
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("addressId", rxBus.id);
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADDRESS_SETDEFAULTS;
            }


            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                RxBus.get().post("address", rxBus);
                if (rxBus.isFromEdit) {
                    RxBus.get().post("back", "back");
                }
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean("设为默认地址", "设置成功", ""));
    }

    public void delete(final AddressRxBus rxBus) {
        final Map<String, String> map = new HashMap<>();
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("addressId", rxBus.id);
        new ApiRequest<SingleBaseBean>() {
            @Override
            protected Map<String, String> getMap() {
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ADDRESS_DELETE;
            }


            @Override
            protected Class<SingleBaseBean> getClx() {
                return SingleBaseBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<SingleBaseBean>() {
            @Override
            public void succes(boolean isWrite, SingleBaseBean bean) {
                RxBus.get().post("address", rxBus);
            }

            @Override
            public void error(boolean isWrite, SingleBaseBean bean, String msg) {

            }
        }).post(new TipLoadingBean("删除地址", "删除成功", ""));
    }
}
