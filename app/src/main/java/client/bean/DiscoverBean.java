package client.bean;

import java.util.ArrayList;

import api.TestConstant;
import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverBean extends ListBaseBean<ArrayList<DiscoverBean.Data>> {
    public static class Data {
        public int type;
        public Data1 data1;
        public Data2 data2;
        public Data3 data3;
        public Data4 data4;
        public ArrayList<String> data5;

        public static class Data1 {
            public String url;
            public String title;
        }

        public static class Data2 {
            public String url;
            public String title;
        }

        public static class Data3 {
            public String title;
        }

        public static class Data4 {
            public String title;
            public ArrayList<String> urls;
        }


    }
}
