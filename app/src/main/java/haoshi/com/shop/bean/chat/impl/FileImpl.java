package haoshi.com.shop.bean.chat.impl;

import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.FileBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class FileImpl extends ChatBaseBean<FileBean, FileBeanDao> {
    public static FileImpl instance;

    public static FileImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new FileImpl(dbName);
        }
        return instance;
    }

    public static FileImpl getInstance() {
        return getInstance("");
    }

    public FileImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getFileBeanDao());
    }

    @Override
    public FileBean select(String id) {
        return getDao().queryBuilder().where(FileBeanDao.Properties.Sign.eq(id)).build().unique();
    }

}
