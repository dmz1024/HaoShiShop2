package client.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import client.bean.FlockBean;
import client.bean.FriendBean;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFriendBean extends ListBaseBean<ArrayList<FindFriendBean.Data>> {
    public static class Data {
        public String title;
        public ArrayList<FriendBean> friendBeen;
    }
}
