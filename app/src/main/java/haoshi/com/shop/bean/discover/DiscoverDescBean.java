package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.BaseBean;
import haoshi.com.shop.bean.AttrsBean;
import interfaces.ImageUrlBaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverDescBean extends BaseBean<DiscoverDescBean.Data> {
    public static class Data {
        /**
         * goodsId : 74
         * goodsName : ceshi1
         * gallery : ["1","2","3"]
         * createTime : 2017-03-21 16:49:55
         * shopPrice : 12.00
         * goodsCatId : 369
         * shopId : 1
         * zan : 1
         * userPhone : 
         * userId : 1
         * isShouCang : 0
         * publishNum : {"publishNum":0,"userId":1,"userPhone":""}
         * fx_logo : 
         * fx_url : 
         * fx_title : 
         * h5Url : http://www.baidu.com/
         * goodsAttr : [{"attrId":3,"attrVal":"河北11","attrName":"起步地点"},{"attrId":4,"attrVal":"邯郸11","attrName":"落脚地点"},{"attrId":5,"attrVal":"153","attrName":"电话"}]
         * goodsComent : {"userPhoto":"","userName":"15HDI2YFW8YL","content":"我来评价了","createTime":"2017-03-19 16:04:40","allPingLun":1,"zanNum":1}
         * goodsTuiJian : [{"goodsName":"ceshixiugai","goodsImg":"http://mall.east-profit.com/1","userName":"wstmart","liulan":100,"pingLunNum":1},{"goodsName":"ceshixiugai","goodsImg":"http://mall.east-profit.com/1","userName":"wstmart","liulan":100,"pingLunNum":1}]
         */

        public String userPhoto;
        public String userName;
        public String goodsId;
        public String goodsName;
        public String createTime;
        public String shopPrice;
        public String goodsCatId;
        public String shopId;
        public String zan;
        public String userPhone;
        public String userId;
        public int isShouCang;
        public String goodsDesc;
        public PublishNumBean publishNum;
        public String h5Url;
        public ArrayList<AllDiscoverCommentBean.Data> goodsComent;
        public ArrayList<GalleryBean> gallery;
        public ArrayList<AttrsBean> goodsAttr;
        public ArrayList<DiscoverBean.Data> goodsTuiJian;
        public String liulan;

        public String shareContent;
        public String fx_url;
        public String fx_title;
        public String fx_logo;

        public static class GalleryBean extends ImageUrlBaseBean {
            public String pic;

            @Override
            public String getUrl() {
                return pic;
            }
        }


        public static class PublishNumBean {
            /**
             * publishNum : 0
             * userId : 1
             * userPhone : 
             */

            public int publishNum;
            public String userId;
            public String userPhone;
        }


        public static class GoodsTuiJianBean {
            /**
             * goodsName : ceshixiugai
             * goodsImg : http://mall.east-profit.com/1
             * userName : wstmart
             * liulan : 100
             * pingLunNum : 1
             */

            public String goodsName;
            public String goodsImg;
            public String userName;
            public int liulan;
            public int pingLunNum;
        }
    }
}
