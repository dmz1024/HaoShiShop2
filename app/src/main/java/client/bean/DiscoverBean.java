package client.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
            public int articleId;
            public int catId;
            public String articleTitle;
            public int userId;
            public String userName;
            public int liulan;
            public int article_appraises;
            public ArrayList<String> img;
        }

        public static class AdsBean {
            public int adId;
            public String adFile;
            public String adName;
            public String adURL;
        }
    }
}
