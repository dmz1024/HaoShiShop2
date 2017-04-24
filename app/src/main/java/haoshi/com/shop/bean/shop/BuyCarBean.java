package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class BuyCarBean extends ListBaseBean<ArrayList<BuyCarBean.Data>> {

    public static class Data {
        /**
         * goodsMoney : 0
         * shopId : 1
         * shopName : WSTMart自营超市
         * userId : 1
         * list : [{"goodsSpecId":"232","cartId":19,"userId":1,"shopId":1,"goodsId":57,"shopQQ":"153289970","shopWangWang":null,"goodsName":"【wstmart超市】陕西大荔冬枣(冰糖枣)500g 10-20g/个 枣子甜枣 水果","shopPrice":"104.00","goodsStock":100,"specPrice":"104.00","specStock":100,"goodsImg":"http://mall.east-profit.com/upload/goods/2017-03/58cf41f1799e0.jpg","isCheck":0,"specIds":"64:59:61","cartNum":3,"goodsCatId":367,"allowBuy":10,"specNames":[{"catName":"选择颜色","itemId":64,"itemName":"黑色"},{"catName":"测试规格","itemId":59,"itemName":"01"},{"catName":"尺码","itemId":61,"itemName":"04"}]}]
         */
        public boolean isChoose;
        public String goodsMoney;
        public String shopId;
        public String shopName;
        public String userId;
        public ArrayList<ListBean> list;
        public boolean isEdit;
        public boolean isHandcar;

        public static class ListBean {
            public boolean isChoose;

            /**
             * goodsSpecId : 232
             * cartId : 19
             * userId : 1
             * shopId : 1
             * goodsId : 57
             * shopQQ : 153289970
             * shopWangWang : null
             * goodsName : 【wstmart超市】陕西大荔冬枣(冰糖枣)500g 10-20g/个 枣子甜枣 水果
             * shopPrice : 104.00
             * goodsStock : 100
             * specPrice : 104.00
             * specStock : 100
             * goodsImg : http://mall.east-profit.com/upload/goods/2017-03/58cf41f1799e0.jpg
             * isCheck : 0
             * specIds : 64:59:61
             * cartNum : 3
             * goodsCatId : 367
             * allowBuy : 10
             * specNames : [{"catName":"选择颜色","itemId":64,"itemName":"黑色"},{"catName":"测试规格","itemId":59,"itemName":"01"},{"catName":"尺码","itemId":61,"itemName":"04"}]
             */

            public String goodsSpecId;
            public String cartId;
            public String userId;
            public String shopId;
            public String goodsId;
            public String shopQQ;
            public String goodsName;
            public double shopPrice;
            public int goodsStock;
            public String specPrice;
            public int specStock;
            public String goodsImg;
            public int isCheck;
            public String specIds;
            public int cartNum;
            public int goodsCatId;
            public int allowBuy;
            public ArrayList<SpecNamesBean> specNames;

            public static class SpecNamesBean {
                /**
                 * catName : 选择颜色
                 * itemId : 64
                 * itemName : 黑色
                 */

                public String catName;
                public int itemId;
                public String itemName;
            }
        }
    }
}
