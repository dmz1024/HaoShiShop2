package client.bean.shop;

import java.util.ArrayList;
import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodAllClassifyBean extends ListBaseBean<ArrayList<GoodAllClassifyBean.Data>> {

    public static class Data {
        public String catName;
        public String catId;
        public String oneCatsUrl;
        public ArrayList<CatBean> list;

        public static class CatBean {
            public String catName;
            public String catId;
            public String parentId;
            public String twoCatsUrl;
        }
    }
}
