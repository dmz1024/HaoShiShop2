package base.bean;

import java.util.ArrayList;

import interfaces.OnChooseAreaIntenface;

/**
 * Created by dengmingzhi on 2017/2/13.
 */

public class ChooseAreaBean extends ListBaseBean<ArrayList<ChooseAreaBean.Data>> {
    public static class Data{
        public String id;
        public String name;
        public boolean idChoose;
    }
}
