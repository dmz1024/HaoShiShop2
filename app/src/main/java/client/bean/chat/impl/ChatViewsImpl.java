package client.bean.chat.impl;

import android.content.Context;

import java.util.List;

import client.bean.chat.dao.ChatBaseBean;
import client.bean.chat.dao.ChatViewBean;
import client.bean.chat.dao.ChatViewBeanDao;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatViewsImpl extends ChatBaseBean<ChatViewBean, ChatViewBeanDao> {
    private String fid;
    private String uid;
    private static ChatViewsImpl instance;

    public ChatViewsImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getChatViewBeanDao());
    }

    public static ChatViewsImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public static ChatViewsImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new ChatViewsImpl(ctx, dbName);
        }
        return instance;
    }

    public String getFid() {
        return fid;
    }

    public ChatViewsImpl setFid(String fid) {
        this.fid = fid;
        return instance;
    }

    public String getUid() {
        return uid;
    }

    public ChatViewsImpl setUid(String uid) {
        this.uid = uid;
        return instance;
    }

    @Override
    protected List<ChatViewBean> getAll() {
        getDao().detachAll();
        return getDao().queryBuilder()
                .where(ChatViewBeanDao.Properties.Uid.eq(getUid()), ChatViewBeanDao.Properties.Fid.eq(getFid()))
                .build().list();
    }



}
