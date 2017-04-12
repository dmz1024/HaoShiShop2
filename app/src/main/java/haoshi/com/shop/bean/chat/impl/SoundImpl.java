package haoshi.com.shop.bean.chat.impl;

import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.SoundBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class SoundImpl extends ChatBaseBean<SoundBean, SoundBeanDao> {
    public static SoundImpl instance;

    public static SoundImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new SoundImpl(dbName);
        }
        return instance;
    }

    public static SoundImpl getInstance() {
        return getInstance("");
    }

    public SoundImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getSoundBeanDao());
    }

    @Override
    public SoundBean select(String id) {
        return getDao().queryBuilder().where(SoundBeanDao.Properties.Sign.eq(id)).build().unique();
    }
}
