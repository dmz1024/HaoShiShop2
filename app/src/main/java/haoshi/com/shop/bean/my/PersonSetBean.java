package haoshi.com.shop.bean.my;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/31.
 */

public class PersonSetBean extends BaseBean<PersonSetBean.Data> {

    public static class Data {
        public int userId;
        public String userPhoto;
        public String userName;
        public String userPhone;
        public String userEmail;
        public int isTrue;
        public int person;
        public int shops;
    }
}
