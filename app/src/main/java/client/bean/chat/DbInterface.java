package client.bean.chat;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import client.bean.chat.dao.DaoSession;


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

    void initDb(Context ctx, String dbName);
}
