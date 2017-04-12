package haoshi.com.shop.bean.my;

import base.bean.AreaBean;

/**
 * Created by dengmingzhi on 2017/3/22.
 */

public class MyAreaBean extends AreaBean {
    public String areaId;
    public String areaName;

    @Override
    public String id() {
        return areaId;
    }

    @Override
    public String name() {
        return areaName;
    }
}
