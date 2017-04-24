package haoshi.com.shop.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/4/24.
 */

public class ShaiXuanBean extends BaseBean<ShaiXuanBean.Content> {
    public static class Content {
        public String keyword;
        public ArrayList<Data> search;

        public static class Data {
            public String name;
            public String value1;
            public String value2;
        }
    }
}
