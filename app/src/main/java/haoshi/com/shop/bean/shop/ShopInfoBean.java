package haoshi.com.shop.bean.shop;

import java.util.List;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/9.
 */

public class ShopInfoBean extends BaseBean<ShopInfoBean.Data> {

    public static class Data{
        public int shopId;
        public String shopImg;
        public String shopName;
        public String shopDesc;
        public String shopCompany;
        public String shopUserId;
        public String shopUserName;
        public String shopUserPhoto;
        public List<AccredsBean> accreds;

        public static class AccredsBean {
            public int shopId;
            public String accredName;
            public String accredImg;
        }
    }

}
