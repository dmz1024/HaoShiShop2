package haoshi.com.shop.bean.zongqinghui;

import java.util.ArrayList;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/3/31.
 */

public class FlockInfoBean extends BaseBean<FlockInfoBean.Data> {


    public static class Data {
        /**
         * groupname : 商淘软件
         * grouplogo :
         * intro :
         * number : 1
         * groupid : 2
         * nums : 120
         * users : [{"name":"mingzi","uid":"33","logo":""},{"name":"15HDI2YFW8YL","uid":"148","logo":""},{"name":"15GPVNXJJ0UR","uid":"147","logo":""},{"name":"15GPUJJG1DYW","uid":"146","logo":""}]
         */

        public String groupname;
        public String grouplogo;
        public String intro;
        public String number;
        public String groupid;
        public String nums;
        public ArrayList<UsersBean> users;

        public static class UsersBean {
            /**
             * name : mingzi
             * uid : 33
             * logo :
             */

            public String name;
            public String uid;
            public String logo;
        }
    }
}
