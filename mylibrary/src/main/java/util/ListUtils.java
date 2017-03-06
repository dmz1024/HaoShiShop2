package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ListUtils {

    public static <D> ArrayList<D> list2Array(List<D> list) {
        int count = list.size();
        ArrayList<D> ds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ds.add(list.get(i));
        }
        return ds;
    }
}
