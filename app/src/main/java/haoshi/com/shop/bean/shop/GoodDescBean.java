package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.BaseBean;
import haoshi.com.shop.bean.AttrsBean;
import haoshi.com.shop.bean.Share;
import interfaces.ImageUrlBaseBean;

/**
 * Created by dengmingzhi on 2017/3/7.
 * 商品详情
 */

public class GoodDescBean extends BaseBean<GoodDescBean.Data> {

    public static class Data {
        /**
         * goodsid : 66
         * shopId : 1
         * gallery : [{"pic":"http://mall.east-profit.com/upload/goods/2017-03/58be47049cb47.jpg"},{"pic":"http://mall.east-profit.com/upload/goods/2017-03/58be4c7674c7c.jpg"},{"pic":"http://mall.east-profit.com/upload/goods/2017-03/58be4c9a81145.png"}]
         * goodsName : 测试1
         * marketPrice : 111.00
         * shopPrice : 222.00
         * goodsStock : 1554
         * saleNum : 0
         * goodsTips : 商品促销信息
         * spec : [{"name":"测试规格","list":[{"itemId":55,"itemName":"测试规格显示1","itemImg":""},{"itemId":56,"itemName":"测试规格显示2","itemImg":""}]},{"name":"尺码","list":[{"itemId":57,"itemName":"13","itemImg":""},{"itemId":58,"itemName":"14","itemImg":""}]}]
         * saleSpec : [{"value":"55:57","sid":216,"marketPrice":"111.00","specPrice":"222.00","specStock":333},{"value":"55:58","sid":217,"marketPrice":"111.00","specPrice":"222.00","specStock":333},{"value":"56:57","sid":218,"marketPrice":"111.00","specPrice":"222.00","specStock":444},{"value":"56:58","sid":219,"marketPrice":"111.00","specPrice":"222.00","specStock":444}]
         * attrs : [{"attrName":"测试属性","attrVal":"测试属性"},{"attrName":"测试属性2","attrVal":"测试属性2"}]
         * favGood : 0
         * comment : []
         */
        public String sid;
        public String url;//图文详情
        public int type;//0普通价1团购价
        public int appraiseNum;//评论数
        public String goodsid;
        public String shopId;
        public String goodsName;
        public String marketPrice;
        public String shopPrice;
        public String goodsStock;
        public Share share;
        public String isDefault;
        public String saleNum;
        public String goodsTips;
        public int favGood;
        public ArrayList<GalleryBean> gallery;
        public ArrayList<SpecBean> spec;
        public ArrayList<SaleSpecBean> saleSpec;
        public ArrayList<AttrsBean> attrs;
        public ArrayList<GoodEvaluateBean.Data> comment;
        public long starttime;
        public long endtime;
        public Serviceid serviceid;

        public static class Serviceid {
            public String userId;
            public String shopName;
            public String shopImg;
            public String shopUserName;
            public String shopUserPhoto;
            public String shopUserId;
        }

        public static class GalleryBean extends ImageUrlBaseBean {
            public String pic;

            @Override
            public String getUrl() {
                return pic;
            }
        }

        public static class SpecBean {
            /**
             * name : 测试规格
             * list : [{"itemId":55,"itemName":"测试规格显示1","itemImg":""},{"itemId":56,"itemName":"测试规格显示2","itemImg":""}]
             */

            public String name;
            public ArrayList<AttrItemBean> list;

            public static class AttrItemBean {
                /**
                 * itemId : 55
                 * itemName : 测试规格显示1
                 * itemImg :
                 */

                public String itemId;
                public String itemName;
                public String itemImg;
            }
        }

        public static class SaleSpecBean {
            /**
             * value : 55:57
             * sid : 216
             * marketPrice : 111.00
             * specPrice : 222.00
             * specStock : 333
             */

            public String value;
            public String sid;
            public String marketPrice;
            public String specPrice;
            public int specStock;
            public String productNo;
        }

    }
}
