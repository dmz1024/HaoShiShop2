package haoshi.com.shop.bean.discover;

import java.util.ArrayList;

import base.bean.BaseBean;
import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/4/13.
 */

public class CustomCat extends BaseBean<ArrayList<CustomCat.Data>> {
    public static class Data implements OnStringInterface{
        public String catName;
        public String catId;

        @Override
        public String getString() {
            return catName;
        }
    }
}
