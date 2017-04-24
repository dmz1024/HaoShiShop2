package haoshi.com.shop.bean.zongqinghui;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/6.
 * 好友资料
 */

public class FriendInfoBean extends BaseBean<FriendInfoBean.Data> {
    public static class Data {
        public String fid;
        public String userName;
        public String loginName;
        public String userPhoto;
        public String userSex;
        public String note;
        public String groupname;
        public int isadd;
    }
}
