package haoshi.com.shop.bean.login;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/13.
 */

public class LoginBean extends BaseBean<LoginBean.Data> {
    public static class Data{
        public String token;
        public String userId;
        public String userName;
        public String userPhone;
    }
}
