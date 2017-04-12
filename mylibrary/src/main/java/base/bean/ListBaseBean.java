package base.bean;

import java.util.ArrayList;

/**
 * Created by dengmingzhi on 2016/11/21.
 */

public class ListBaseBean<T> extends BaseBean<T> {
    public PageInfo page_info;

    public static class PageInfo {
        public int page_index;
        public int page_count;
    }

}
