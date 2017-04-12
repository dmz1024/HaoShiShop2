package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverTabBean extends BaseBean<ArrayList<DiscoverTabBean.Data>> {
    public static class Data {
        public String catId;
        public String parentId;
        public String catName;
        public String catsImg;
    }
}
