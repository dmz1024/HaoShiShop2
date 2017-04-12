package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodGroupActivityBean extends ListBaseBean<ArrayList<GoodGroupActivityBean.Data>> {

    public static class Data {
        public String goodsId;
        public String goodsName;
        public String shopPrice;
        public String marketPrice;
        public String goodsImg;
    }
}
