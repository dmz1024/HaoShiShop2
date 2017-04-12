package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodEvaluateBean extends ListBaseBean<ArrayList<GoodEvaluateBean.Data>> {
    public static class Data {
        public String userPhoto;
        public String loginName;
        public int score;
        public String content;
        public String createTime;
    }
}
