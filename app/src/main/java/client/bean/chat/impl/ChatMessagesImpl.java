package client.bean.chat.impl;

import android.content.Context;

import java.util.List;

import client.bean.chat.dao.ChatMessageBean;

import client.bean.chat.dao.ChatBaseBean;
import client.bean.chat.dao.ChatMessageBeanDao;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class ChatMessagesImpl extends ChatBaseBean<ChatMessageBean, ChatMessageBeanDao> {
    public static ChatMessagesImpl instance;

    public static ChatMessagesImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new ChatMessagesImpl(ctx, dbName);
        }
        return instance;
    }

    public static ChatMessagesImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }


    public ChatMessagesImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getChatMessageBeanDao());
    }

    @Override
    public ChatMessageBean select(String id) {
        return getDao().queryBuilder().where(ChatMessageBeanDao.Properties.FId.eq(id)).build().unique();
    }

    @Override
    protected List<ChatMessageBean> getAll() {
        return getDao().queryBuilder().orderDesc(ChatMessageBeanDao.Properties.Time).build().list();
    }
}
