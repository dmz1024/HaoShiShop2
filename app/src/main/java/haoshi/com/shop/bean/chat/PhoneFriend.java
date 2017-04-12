package haoshi.com.shop.bean.chat;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/4/11.
 */

public class PhoneFriend extends ListBaseBean<ArrayList<PhoneFriend.Data>> {
    public static class Data{
        public String userId;
        public String name;
        public int isadd;
        public String userPhone;
        public String userPhoto;
    }
}
