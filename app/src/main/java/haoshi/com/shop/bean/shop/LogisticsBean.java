package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/4/24.
 */

public class LogisticsBean extends ListBaseBean<ArrayList<LogisticsBean.Data>> {
    public static class Data{
        public String logisticsTime;
        public String logisticsTrack;
    }

    public Info info;

    public static class Info{
        public String expressId;
        public String expressName;
        public String expressNo;
    }
}
