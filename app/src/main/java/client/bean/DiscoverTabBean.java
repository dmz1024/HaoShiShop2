package client.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverTabBean extends BaseBean<ArrayList<DiscoverTabBean.Data>> {
    public static class Data {
        public String catId;
        public String parentId;
        public String catName;
        public String catImg;
    }
}
