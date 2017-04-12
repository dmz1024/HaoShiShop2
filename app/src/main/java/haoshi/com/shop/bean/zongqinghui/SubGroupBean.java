package haoshi.com.shop.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.BaseBean;
import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/3/31.
 */

public class SubGroupBean extends BaseBean<ArrayList<SubGroupBean.Data>> {

    public static class Data implements OnStringInterface {
        /**
         * id : 30
         * name : 如果
         * number : 0
         */

        public String id;
        public String name;
        public int number;

        @Override
        public String getString() {
            return name;
        }
    }
}
