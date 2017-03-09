package client.bean.chat.impl;

import android.content.Context;

import client.bean.chat.FileBean;
import client.bean.chat.FileBeanDao;
import client.bean.chat.PhotoBean;
import client.bean.chat.PhotoBeanDao;
import client.bean.chat.dao.ChatBaseBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class FileImpl extends ChatBaseBean<FileBean, FileBeanDao> {
    public static FileImpl instance;

    public static FileImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new FileImpl(ctx, dbName);
        }
        return instance;
    }

    public static FileImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public FileImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getFileBeanDao());
    }

}
