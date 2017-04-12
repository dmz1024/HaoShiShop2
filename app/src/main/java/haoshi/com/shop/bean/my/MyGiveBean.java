package haoshi.com.shop.bean.my;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class MyGiveBean extends ListBaseBean<ArrayList<MyGiveBean.Data>> {
    public static class Data{
        public String userName;
        public String orderId;
        public String createTime;
        public String needPay;
        public int orderStatus;
    }
}
