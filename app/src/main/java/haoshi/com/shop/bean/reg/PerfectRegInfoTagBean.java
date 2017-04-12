package haoshi.com.shop.bean.reg;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/3/23.
 */

public class PerfectRegInfoTagBean extends ListBaseBean<ArrayList<PerfectRegInfoTagBean.Data>> {
    public static class Data {
        /**
         * tid : 17
         * content : 姓名
         * logo : http://mall.east-profit.com/
         * key : 1
         * value : truename
         */

        public String tid;
        public String content;
        public String logo;
        public String hint;
        public int key;
        //key=0 取list(tid为key)  key=2访问地区(fid是key value是第三级id，tid为)
        // key=1自己输入（value的值作为key）
        public String value;
        public boolean isChoose;
        public ArrayList<ChooseListBean> list;
        public String result;
        public String chooseContent;


    }

    public static class ChooseListBean implements OnStringInterface {
        public String content;
        public String fid;
        public String tid;

        @Override
        public String getString() {
            return content;
        }
    }

    @Override
    public ArrayList<Data> getData() {
        for (PerfectRegInfoTagBean.Data d : data
                ) {
            d.hint = d.content;
        }
        return data;
    }
}
