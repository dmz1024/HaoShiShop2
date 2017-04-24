package haoshi.com.shop.bean.shop;

import java.util.ArrayList;

import base.bean.BaseBean;
import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/4/18.
 */

public class ChooseWLNameBean extends BaseBean<ArrayList<ChooseWLNameBean.Data>> {
    public static class Data implements OnStringInterface{
        public String expressId;
        public String expressName;
        public String expressCode;

        @Override
        public String getString() {
            return expressName;
        }
    }
}
