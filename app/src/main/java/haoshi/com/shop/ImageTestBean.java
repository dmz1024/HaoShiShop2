package haoshi.com.shop;

import java.util.ArrayList;

import activity.EaluationListBean;
import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class ImageTestBean extends ListBaseBean<ImageTestBean.Data> {
    public static class Data{
        public int totalCount;
        public int pageNo;
        public int pageCount;
        public int goodCount;
        public int badCount;
        public int middleCount;
        //    好评率
        public String goodPD;
        public ArrayList<EaluationListBean> evaluataions;
    }
}
