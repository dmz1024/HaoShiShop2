package haoshi.com.shop.bean.chat.impl;

import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.TextBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class TextImpl extends ChatBaseBean<TextBean, TextBeanDao> {
    public static TextImpl instance;

    public static TextImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new TextImpl(dbName);
        }
        return instance;
    }

    public static TextImpl getInstance() {
        return getInstance("");
    }

    @Override
    public TextBean select(String id) {
        return getDao().queryBuilder().where(TextBeanDao.Properties.Sign.eq(id)).build().unique();
    }

    public TextImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getTextBeanDao());
    }

}
