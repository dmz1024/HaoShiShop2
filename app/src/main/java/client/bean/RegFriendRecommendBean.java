package client.bean;

import java.util.ArrayList;

import base.bean.BaseBean;
import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class RegFriendRecommendBean extends BaseBean<RegFriendRecommendBean.Data> {
    public static class Data {
        public ArrayList<FlockBean> flocks;
        public ArrayList<FriendBean> friends;
    }
}
