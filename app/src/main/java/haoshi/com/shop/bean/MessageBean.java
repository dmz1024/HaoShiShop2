package haoshi.com.shop.bean;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class MessageBean extends ListBaseBean<ArrayList<MessageBean.Data>> {
    public static class Data{
        public String createTime;
        public String msgContent;
        public String ID;
        public String msgStatus;
    }
}
