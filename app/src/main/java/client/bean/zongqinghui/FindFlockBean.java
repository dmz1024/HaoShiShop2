package client.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import client.bean.FlockBean;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFlockBean extends ListBaseBean<ArrayList<FindFlockBean.Data>> {
    public static class Data {
        public String title;
        public ArrayList<FlockBean> flockBeen;
    }
}
