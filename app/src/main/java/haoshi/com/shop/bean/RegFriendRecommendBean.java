package haoshi.com.shop.bean;

import java.util.ArrayList;

import base.bean.BaseBean;
import haoshi.com.shop.bean.zongqinghui.FlockBean;
import haoshi.com.shop.bean.zongqinghui.FriendBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class RegFriendRecommendBean extends BaseBean<RegFriendRecommendBean.Data> {
    public static class Data {
        public ArrayList<FlockBean> group;
        public ArrayList<FriendBean> users;
    }
}
