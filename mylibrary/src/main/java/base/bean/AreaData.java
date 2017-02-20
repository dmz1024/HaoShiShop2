package base.bean;

import interfaces.OnChooseAreaIntenface;

/**
 * Created by dengmingzhi on 2017/2/13.
 */

public class AreaData implements OnChooseAreaIntenface {
    public String id;
    public String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
