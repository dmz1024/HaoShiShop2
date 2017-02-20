package base.bean;

import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/2/16.
 */

public class ChooseStringBean implements OnStringInterface {
    public String title;
    @Override
    public String getString() {
        return title;
    }

    public ChooseStringBean(String title) {
        this.title = title;
    }
}
