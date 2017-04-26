package haoshi.com.shop.bean.my;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/3/31.
 */

public class PersonInfoBean extends ListBaseBean<ArrayList<PersonInfoBean.Data>> {
    public static class Data {

        /**
         * ID : 17
         * key : 1
         * list : []
         * name : 姓名
         * text : truename
         * value : rthj
         */

        public String ID;
        public int key;
        public String name;
        public String text;
        public String value;
        public ArrayList<ListBean> list;
        public String result;
    }

    public static class ListBean implements OnStringInterface {
        public String content;
        public String fid;
        public String tid;
        public ArrayList<ListBeans> lists;

        @Override
        public String getString() {
            return content;
        }
    }
    public static class ListBeans implements OnStringInterface {
        public String content;
        public String fid;
        public String pid;

        @Override
        public String getString() {
            return content;
        }
    }

}
