package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/22.
 */

public class AffirmBuyBean extends BaseBean<AffirmBuyBean.Data> {

    public static class Data {

        public AddressBean address;
        public String goodsTotalMoney;
        public ArrayList<ShopBean> shop;
        public ArrayList<PaymentsBean> payments;

        public static class AddressBean {
            public String addressId;
            public String userName;
            public String userPhone;
            public String areaName;
            public String userAddress;
        }

        public static class ShopBean {
            public String shopId;
            public String shopName;
            public String goodsMoney;
            public double freight;
            public ArrayList<GoodsBean> goods;

            public static class GoodsBean {
                public String goodsId;
                public String goodsName;
                public String goodsImg;
                public String cartNum;
                public String shopPrice;
                public String goodsStock;
                public ArrayList<SpecNamesBean> specNames;

                public static class SpecNamesBean {
                    /**
                     * catName : 选择颜色
                     * itemId : 64
                     * itemName : 黑色
                     */

                    public String catName;
                    public String itemId;
                    public String itemName;
                }
            }
        }

        public static class PaymentsBean {
            public String payid;
            public String payName;
        }
    }
}
