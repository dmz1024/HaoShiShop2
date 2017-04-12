package haoshi.com.shop.bean.chat.dao;

import android.text.TextUtils;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

import haoshi.com.shop.bean.chat.DbInterface;
import haoshi.com.shop.constant.UserInfo;
import util.ContextUtil;
import util.ListUtils;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class ChatBaseBean<D, T extends AbstractDao> implements DbInterface<D> {
    protected ArrayList<D> datas;
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

    public ChatBaseBean(String dbName) {
        initDb(TextUtils.isEmpty(dbName) ? "is" + UserInfo.userId + "data.db" : "is" + UserInfo.userId + dbName);
    }


    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void deleteAll() {
        getDao().deleteAll();
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
    public void initDb(String dbName) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(ContextUtil.getCtx().getApplicationContext(), dbName, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

}
