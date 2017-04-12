package haoshi.com.shop.bean;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ZanBean extends ListBaseBean<ArrayList<ZanBean.Data>> {
    public static class Data{
        public String articleId;
        public String createtime;
        public String title;
        public int isSee;
        public String userName;
    }
}
