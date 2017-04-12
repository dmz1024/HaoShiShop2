package haoshi.com.shop.bean.chat.impl;

import java.util.List;

import haoshi.com.shop.bean.chat.dao.ChatBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendGroupBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendGroupBeanDao;

/**
 * Created by dengmingzhi on 2017/3/3.
 */

public class ChatFriendGroupImpl extends ChatBaseBean<ChatFriendGroupBean, ChatFriendGroupBeanDao> {
    public static ChatFriendGroupImpl instance;

    public static ChatFriendGroupImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new ChatFriendGroupImpl(dbName);
        }
        return instance;
    }

    public static ChatFriendGroupImpl getInstance() {
        return getInstance("");
    }

    public ChatFriendGroupImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getChatFriendGroupBeanDao());
    }


    @Override
    protected List<ChatFriendGroupBean> getAll() {
//        getDaoSession().getChatFriendGroupBeanDao().detachAll();
        return super.getAll();
    }
}
