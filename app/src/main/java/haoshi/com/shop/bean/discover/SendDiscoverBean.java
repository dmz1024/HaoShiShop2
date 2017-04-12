package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/24.
 */

public class SendDiscoverBean extends BaseBean<ArrayList<SendDiscoverBean.Data>> {

    public static class Data {
        /**
         * goodsCatId : 403
         * attrName : 多选框
         * attrType : 1
         * attrVal : 热销,热卖,热推
         * attrId : 55
         */

        public String goodsCatId;
        public String attrName;
        public int attrType;
        public String attrVal;
        public String attrId;
        public String content;
        public String aid;

        public Data() {
        }

        public Data(String attrName, int attrType) {
            this.attrName = attrName;
            this.attrType = attrType;
        }
    }
}
