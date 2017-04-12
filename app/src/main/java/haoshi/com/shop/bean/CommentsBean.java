package haoshi.com.shop.bean;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class CommentsBean extends ListBaseBean<ArrayList<CommentsBean.Data>> {
    public static class Data{
        public String userId;
        public String goodsId;
        public String userName;
        public String createtime;
        public String goodsName;
        public String title;
        public int isSee;
    }
}
