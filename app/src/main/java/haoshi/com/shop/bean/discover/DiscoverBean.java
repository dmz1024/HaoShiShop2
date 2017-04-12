package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverBean extends ListBaseBean<ArrayList<DiscoverBean.Data>> {

    public static class Data {
        public ListBean list;
        public int type;
        public ArrayList<AdsBean> ads;

        public static class ListBean {
            public String goodsId;
            public String goodsCatId;
            public String goodsName;
            public String userId;
            public String userName;
            public String liulan;
            public String article_appraises;
            public ArrayList<String> gallery;
        }

        public static class AdsBean {
            public String adId;
            public String adFile;
            public String adName;
            public String adURL;
        }
    }
}
