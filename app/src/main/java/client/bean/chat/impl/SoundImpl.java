package client.bean.chat.impl;

import android.content.Context;

import client.bean.chat.PhotoBean;
import client.bean.chat.PhotoBeanDao;
import client.bean.chat.SoundBean;
import client.bean.chat.SoundBeanDao;
import client.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class SoundImpl extends ChatBaseBean<SoundBean, SoundBeanDao> {
    public static SoundImpl instance;

    public static SoundImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new SoundImpl(ctx, dbName);
        }
        return instance;
    }

    public static SoundImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public SoundImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getSoundBeanDao());
    }

}
