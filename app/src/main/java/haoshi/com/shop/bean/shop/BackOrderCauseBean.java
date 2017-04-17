package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/4/17.
 */

public class BackOrderCauseBean extends ListBaseBean<ArrayList<BackOrderCauseBean.Data>> {
    public static class Data implements OnStringInterface{
        public String dataName;
        public String ID;

        @Override
        public String getString() {
            return dataName;
        }
    }

}
