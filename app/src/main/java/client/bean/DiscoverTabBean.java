package client.bean;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverTabBean extends BaseBean<ArrayList<DiscoverTabBean.Data>> {
    public static class Data{
        public String title;
        public String url;

        public Data(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public Data() {
        }
    }
}
