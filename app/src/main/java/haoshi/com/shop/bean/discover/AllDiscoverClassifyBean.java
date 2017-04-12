package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class AllDiscoverClassifyBean extends ListBaseBean<ArrayList<AllDiscoverClassifyBean.Data>> {
    public static class Data{
        public String catId;
        public String catImg;
        public String catName;
        public String parentId;
        public ArrayList<Cats> cats;
    }

    public static class Cats{
        public String catId;
        public String catImg;
        public String catName;
        public String parentId;
    }
}
