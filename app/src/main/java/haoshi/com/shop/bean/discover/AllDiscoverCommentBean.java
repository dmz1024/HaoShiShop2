package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AllDiscoverCommentBean extends ListBaseBean<ArrayList<AllDiscoverCommentBean.Data>> {
    public static class Data {
        public String createTime;
        public String content;
        public String userId;
        public String userName;
        public String userPhoto;
        public String goodsId;
        public int zan;
    }
}
