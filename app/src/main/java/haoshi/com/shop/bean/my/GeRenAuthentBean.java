package haoshi.com.shop.bean.my;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/30.
 */

public class GeRenAuthentBean extends BaseBean<GeRenAuthentBean.Data> {

    public static class Data {
        public String trueName;
        public String card;
        public ArrayList<String> cardImg;
    }
}
