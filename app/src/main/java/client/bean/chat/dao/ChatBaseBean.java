package client.bean.chat.dao;

import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

import client.bean.chat.DbInterface;
import util.ListUtils;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public  class ChatBaseBean<D, T extends AbstractDao> implements DbInterface<D> {
    protected ArrayList<D> datas;
    private Context ctx;
    private String dbName;
    private T dao;
    private DaoSession daoSession;

    public T getDao() {
        return dao;
    }

    public ChatBaseBean setDao(T dao) {
        this.dao = dao;
        return this;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public ChatBaseBean setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        return this;
    }

    public ChatBaseBean(Context ctx, String dbName) {
        this.ctx = ctx.getApplicationContext();
        initDb(this.ctx, TextUtils.isEmpty(dbName) ? "data.db" : dbName);
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public D select(String id) {
        return null;
    }

    @Override
    public ArrayList<D> selectAll() {
        return ListUtils.list2Array(getAll());
    }

    protected List<D> getAll() {
        return dao.loadAll();
    }

    @Override
    public void update(D d) {
        dao.update(d);
    }

    @Override
    public void delete(D t) {
        dao.delete(t);
    }


    @Override
    public void add(D d) {
        dao.insert(d);
    }

    @Override
    public void addAll(ArrayList<D> ds) {
        for (D d : ds) {
            dao.insert(d);
        }
    }

    @Override
    public ArrayList<D> getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        setDatas();
        return datas;
    }

    @Override
    public void setDatas() {
        datas.clear();
        datas.addAll(selectAll());
    }


    @Override
    public void initDb(Context ctx, String dbName) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(ctx, dbName, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

}
