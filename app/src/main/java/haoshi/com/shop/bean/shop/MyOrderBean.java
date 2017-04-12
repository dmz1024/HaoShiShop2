package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class MyOrderBean extends ListBaseBean<ArrayList<MyOrderBean.Data>> {
    public static class Data {
        /**
         * orderId : 12
         * orderNo : 100000471
         * shopName : WSTMart自营超市
         * shopId : 1
         * goodsMoney : 8787.00
         * totalMoney : 8795.00
         * realTotalMoney : 8795.00
         * orderStatus : -2
         * deliverType : 送货上门
         * deliverMoney : 8.00
         * isPay : 0
         * payType : 1
         * payFrom : 0
         * needPay : 8795.00
         * isAppraise : 0
         * isRefund : 0
         * orderSrc : 0
         * createTime : 2017-03-23 15:53:09
         * complainId : null
         * refundId : null
         * list : [{"id":16,"orderId":12,"goodsId":57,"goodsNum":87,"goodsPrice":"101.00","goodsSpecId":"229","goodsSpecNames":"选择颜色：红色、测试规格：03、尺码：02","goodsName":"【wstmart超市】陕西大荔冬枣(冰糖枣)500g 10-20g/个 枣子甜枣 水果","goodsImg":"http://mall.east-profit.com/upload/goods/2017-03/58cf41f1799e0.jpg","commissionRate":"0.00"}]
         * isComplain :
         * payTypeName : 在线支付
         * status : 待支付
         */

        public String orderId;
        public String orderNo;
        public String shopName;
        public String shopId;
        public String goodsMoney;
        public String totalMoney;
        public String realTotalMoney;
        public int orderStatus;
        public String deliverType;
        public String deliverMoney;
        public String isPay;
        public String payType;
        public String payFrom;
        public String needPay;
        public int isAppraise;
        public int isRefund;
        public String orderSrc;
        public String createTime;
        public String isComplain;
        public String payTypeName;
        public String status;
        public ArrayList<OrderGoodBean> list;

        public static class OrderGoodBean {
            /**
             * id : 16
             * orderId : 12
             * goodsId : 57
             * goodsNum : 87
             * goodsPrice : 101.00
             * goodsSpecId : 229
             * goodsSpecNames : 选择颜色：红色、测试规格：03、尺码：02
             * goodsName : 【wstmart超市】陕西大荔冬枣(冰糖枣)500g 10-20g/个 枣子甜枣 水果
             * goodsImg : http://mall.east-profit.com/upload/goods/2017-03/58cf41f1799e0.jpg
             * commissionRate : 0.00
             */

            public int id;
            public int orderId;
            public int goodsId;
            public int goodsNum;
            public String goodsPrice;
            public String goodsSpecId;
            public String goodsSpecNames;
            public String goodsName;
            public String goodsImg;
            public String commissionRate;
        }
    }

}
