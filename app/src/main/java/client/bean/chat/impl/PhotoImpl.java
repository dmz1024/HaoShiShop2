package client.bean.chat.impl;

import android.content.Context;

import client.bean.chat.PhotoBean;
import client.bean.chat.PhotoBeanDao;
import client.bean.chat.dao.ChatBaseBean;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.chat.dao.ChatFriendBeanDao;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class PhotoImpl extends ChatBaseBean<PhotoBean, PhotoBeanDao> {
    public static PhotoImpl instance;

    public static PhotoImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new PhotoImpl(ctx, dbName);
        }
        return instance;
    }

    public static PhotoImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public PhotoImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getPhotoBeanDao());
    }

}
