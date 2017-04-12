package haoshi.com.shop.bean.login;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/13.
 */

public class RegBean extends BaseBean<RegBean.Data> {
    public static class Data {
        public String userId;
        public String token;
    }
}
