package haoshi.com.shop.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class FriendDynamicBean extends ListBaseBean<ArrayList<FriendDynamicBean.Data>> {
    public Info info;

    public static class Data {
        public ListBean list;
        public int type;

        public static class ListBean {
            public String goodsName;
            public String forwardDesc;
            public String goodsCatId;
            public String goodsId;
            public String userId;
            public String userName;
            public String userPhoto;
            public String visitNum;
            public String createTime;
            public String zan;
            public String liulan;
            public String article_appraises;
            public ArrayList<String> gallery;
        }

    }

    public static class Info {
        public String userId;
        public String userName;
        public String userPhoto;
    }


}
