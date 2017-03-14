package client.bean.shop;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class GoodEvaluateBean extends ListBaseBean<ArrayList<GoodEvaluateBean.Data>> {
    public static class Data {
        public String userPhoto;
        public String loginName;
        public int goodsScore;
        public int serviceScore;
        public int timeScore;
        public String content;
        public String createTime;
    }
}
