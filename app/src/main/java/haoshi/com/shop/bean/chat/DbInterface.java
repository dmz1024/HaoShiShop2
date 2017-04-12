package haoshi.com.shop.bean.chat;

import java.util.ArrayList;


/**
 * Created by dengmingzhi on 2017/3/1.
 */

public interface DbInterface<D> {
    D select(String id);

    ArrayList<D> selectAll();

    void update(D t);

    void delete(D t);

    void add(D t);

    void addAll(ArrayList<D> ts);

    ArrayList<D> getDatas();

    void setDatas();

    void initDb(String dbName);
}
