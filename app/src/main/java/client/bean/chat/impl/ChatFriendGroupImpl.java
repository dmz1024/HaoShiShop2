package client.bean.chat.impl;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import client.bean.chat.dao.ChatBaseBean;
import client.bean.chat.dao.ChatFriendBean;
import client.bean.chat.dao.ChatFriendGroupBean;
import client.bean.chat.dao.ChatFriendGroupBeanDao;

/**
 * Created by dengmingzhi on 2017/3/3.
 */

public class ChatFriendGroupImpl extends ChatBaseBean<ChatFriendGroupBean, ChatFriendGroupBeanDao> {
    public static ChatFriendGroupImpl instance;

    public static ChatFriendGroupImpl getInstance(Context ctx, String dbName) {
        if (instance == null) {
            instance = new ChatFriendGroupImpl(ctx, dbName);
        }
        return instance;
    }

    public static ChatFriendGroupImpl getInstance(Context ctx) {
        return getInstance(ctx, "");
    }

    public ChatFriendGroupImpl(Context ctx, String dbName) {
        super(ctx, dbName);
        setDao(getDaoSession().getChatFriendGroupBeanDao());
    }

}
