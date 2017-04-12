package haoshi.com.shop.bean.chat.impl;

import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.PhotoBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class PhotoImpl extends ChatBaseBean<PhotoBean, PhotoBeanDao> {
    public static PhotoImpl instance;

    public static PhotoImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new PhotoImpl(dbName);
        }
        return instance;
    }

    public static PhotoImpl getInstance() {
        return getInstance("");
    }

    public PhotoImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getPhotoBeanDao());
    }

    @Override
    public PhotoBean select(String id) {
        return getDao().queryBuilder().where(PhotoBeanDao.Properties.Sign.eq(id)).build().unique();
    }
}
