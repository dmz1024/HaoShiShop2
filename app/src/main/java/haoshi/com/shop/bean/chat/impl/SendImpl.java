package haoshi.com.shop.bean.chat.impl;

import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.TextBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.bean.chat.dao.SendBeanDao;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class SendImpl extends ChatBaseBean<SendBean, SendBeanDao> {
    public static SendImpl instance;

    public static SendImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new SendImpl(dbName);
        }
        return instance;
    }

    public static SendImpl getInstance() {
        return getInstance("");
    }

    @Override
    public SendBean select(String id) {
        return getDao().queryBuilder().where(SendBeanDao.Properties.Sign.eq(id)).build().unique();
    }

    public SendImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getSendBeanDao());
    }

}
