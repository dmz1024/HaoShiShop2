package haoshi.com.shop.bean.my;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2016/12/8.
 */

public class AddressBean extends ListBaseBean<ArrayList<AddressBean.Data>> {
    public static class Data {
        public String addressId;
        public String userAddress;
        public String userName;
        public String userPhone;
        public String areaName;
        public int isDefault;
        public String areaId;
    }
}
