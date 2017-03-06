package client.bean.chat.impl;

import android.content.Context;

import client.bean.chat.PhotoBean;
import client.bean.chat.PhotoBeanDao;
import client.bean.chat.TextBean;
import client.bean.chat.TextBeanDao;
import client.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class TextImpl extends ChatBaseBean<TextBean, TextBeanDao> {
    public static TextImpl instance;

    public static TextImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new TextImpl(ctx, dbName);
        }
        return instance;
    }

    public static TextImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public TextImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getTextBeanDao());
    }

}
