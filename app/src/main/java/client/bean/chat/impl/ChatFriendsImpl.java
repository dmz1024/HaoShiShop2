package client.bean.chat.impl;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import client.bean.chat.dao.ChatBaseBean;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.chat.dao.ChatFriendBeanDao;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class ChatFriendsImpl extends ChatBaseBean<ChatFriendBean, ChatFriendBeanDao> {
    public static ChatFriendsImpl instance;

    @Override
    public ChatFriendBean select(String id) {
        return getDao().queryBuilder().where(ChatFriendBeanDao.Properties.Uid.eq(id)).build().unique();
    }

    public static ChatFriendsImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new ChatFriendsImpl(ctx, dbName);
        }
        return instance;
    }

    public static ChatFriendsImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public ChatFriendsImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getChatFriendBeanDao());
    }


    @Override
    protected List<ChatFriendBean> getAll() {
        QueryBuilder<ChatFriendBean> queryBuilder = getDao().queryBuilder();
        return queryBuilder.where(ChatFriendBeanDao.Properties.Type.eq(2)).build().list();
    }

}
