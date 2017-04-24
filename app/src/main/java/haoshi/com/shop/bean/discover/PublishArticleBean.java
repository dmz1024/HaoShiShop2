package haoshi.com.shop.bean.discover;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2017/4/20.
 */

public class PublishArticleBean extends BaseBean<PublishArticleBean.Data> {
    public static class Data {
        public int isPublish;
        public int shops;
        public int person;
        public int isTrue;
    }
}
