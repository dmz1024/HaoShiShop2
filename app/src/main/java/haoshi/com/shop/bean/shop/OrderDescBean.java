package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/30.
 */

public class OrderDescBean extends BaseBean<OrderDescBean.Data> {

    public static class Data {
        /**
         * orderId : 76
         * orderNo : 100001333
         * shopId : 1
         * userId : 148
         * orderStatus : 16
         * goodsMoney : 85.00
         * deliverType : 0
         * deliverMoney : 5.00
         * totalMoney : 90.00
         * realTotalMoney : 90.00
         * payType : 1
         * payFrom : 0
         * isPay : 0
         * areaId : 632322
         * areaIdPath : 630000_632300_632322_
         * userName : 邓如果
         * userAddress : 青海省黄南藏族自治州尖扎县估计民工民工
         * userPhone : 18326167257
         * orderScore : 0
         * isInvoice : 0
         * invoiceClient :
         * orderRemarks :
         * orderSrc : 0
         * needPay : 90.00
         * isRefund : 0
         * isAppraise : 0
         * cancelReason : 0
         * rejectReason : 0
         * rejectOtherReason : null
         * isClosed : 0
         * goodsSearchKeys : null
         * orderunique : 149084684929705840
         * receiveTime : null
         * deliveryTime : null
         * expressId : null
         * expressNo : null
         * tradeNo : null
         * dataFlag : 1
         * createTime : 2017-03-30 12:07:29
         * settlementId : 0
         * commissionFee : 0.00
         * type : 0
         * expressName : null
         * shopTel : 13888888888
         * shopName : 郝氏商城自营超市
         * shopQQ : 153289970
         * shopWangWang :
         * refundRemark : null
         * refundStatus : null
         * refundTime : null
         * backMoney : null
         * accountNumber : null
         * refundExplain : null
         * voucherImg : []
         * log : [{"logId":111,"orderId":76,"orderStatus":-2,"logContent":"下单成功，等待用户支付","logUserId":148,"logType":0,"logTime":"2017-03-30 12:07:29"}]
         * goods : [{"id":85,"orderId":76,"goodsId":103,"goodsNum":1,"goodsPrice":"85.00","goodsSpecId":"264","goodsSpecNames":"颜色：白色","goodsName":"时尚个性范韩版新款蝙蝠袖短款条纹连帽棒球服外套","goodsImg":"http://mall.east-profit.com/http://mall.east-profit.com/upload/goods/2017-03/58d4b9b4371fd.png","commissionRate":"0.00"}]
         */

        public String orderId;
        public String orderNo;
        public String shopId;
        public String userId;
        public int orderStatus;
        public String goodsMoney;
        public int deliverType;
        public String deliverMoney;
        public String totalMoney;
        public String realTotalMoney;
        public int payType;
        public int payFrom;
        public int isPay;
        public String areaId;
        public String areaIdPath;
        public String userName;
        public String userAddress;
        public String userPhone;
        public int orderScore;
        public int isInvoice;
        public String invoiceClient;
        public String orderRemarks;
        public int orderSrc;
        public String needPay;
        public int isRefund;
        public int isAppraise;
        public int cancelReason;
        public int rejectReason;
        public Object rejectOtherReason;
        public int isClosed;
        public Object goodsSearchKeys;
        public String orderunique;
        public Object receiveTime;
        public Object deliveryTime;
        public Object expressId;
        public Object expressNo;
        public Object tradeNo;
        public int dataFlag;
        public String createTime;
        public String settlementId;
        public String commissionFee;
        public int type;
        public Object expressName;
        public String shopTel;
        public String shopName;
        public String shopQQ;
        public String shopWangWang;
        public Object refundRemark;
        public Object refundStatus;
        public Object refundTime;
        public Object backMoney;
        public Object accountNumber;
        public Object refundExplain;
        public ArrayList<?> voucherImg;
        public ArrayList<LogBean> log;
        public ArrayList<MyOrderBean.Data.OrderGoodBean> goods;

        public static class LogBean {
            /**
             * logId : 111
             * orderId : 76
             * orderStatus : -2
             * logContent : 下单成功，等待用户支付
             * logUserId : 148
             * logType : 0
             * logTime : 2017-03-30 12:07:29
             */
            public String logContent;
            public String logTime;
            public String color="#666666";

            public LogBean(String logContent, String logTime, String color) {
                this.logContent = logContent;
                this.logTime = logTime;
                this.color = color;
            }

            public LogBean() {
            }

            public LogBean(String logContent, String logTime) {
                this.logContent = logContent;
                this.logTime = logTime;
            }
        }

    }
}
