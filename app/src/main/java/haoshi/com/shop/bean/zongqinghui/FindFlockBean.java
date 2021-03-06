package haoshi.com.shop.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFlockBean extends ListBaseBean<ArrayList<FindFlockBean.Data>> {
    public User user;

    public static class Data {
        public String fid;
        public String type;
        public String keyword;
        public String name;
        public ArrayList<FlockBean> data;
    }

    public static class User {
        public String name;
        public String userPhoto;
        public String property;
    }

    @Override
    public ArrayList<Data> getData() {
        ArrayList<FindFlockBean.Data> d = new ArrayList<>();
        for (FindFlockBean.Data f : data) {
            if (f.data != null && f.data.size() > 0) {
                d.add(f);
            }
        }
        return d;
    }
}
