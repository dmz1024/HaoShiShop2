package haoshi.com.shop.bean.my;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/4/13.
 */

public class MessageSetBean extends BaseBean<MessageSetBean.Data> {
    public static class Data {
        public int isComments;
        public int isDeliverGoods;
        public int isFabulous;
        public int isNotice;
    }
}
