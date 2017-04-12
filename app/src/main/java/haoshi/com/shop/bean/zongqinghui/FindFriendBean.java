package haoshi.com.shop.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFriendBean extends ListBaseBean<ArrayList<FindFriendBean.Data>> {
    public static class Data {
        public String fid;
        public String type;
        public String keyword;
        public String name;
        public ArrayList<FriendBean> data;
    }
    public FindFlockBean.User user;
}
