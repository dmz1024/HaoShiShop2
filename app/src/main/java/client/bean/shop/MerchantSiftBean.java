package client.bean.shop;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class MerchantSiftBean extends ListBaseBean<ArrayList<MerchantSiftBean.Data>> {

    public static class Data {
        public int shopId;
        public String shopImg;
        public String shopName;
        public String shopCompany;
        public int totalScore;
        public int totalUsers;
        public int goodsScore;
        public int goodsUsers;
        public int serviceScore;
        public int serviceUsers;
        public int timeScore;
        public int timeUsers;
        public String catshops;
        public String appraisesCount;
        public ArrayList<AccredsBean> accreds;

        public static class AccredsBean {
            public int shopId;
            public String accredName;
            public String accredImg;
        }
    }
}
