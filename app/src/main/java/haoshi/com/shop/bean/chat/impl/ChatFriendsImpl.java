package haoshi.com.shop.bean.chat.impl;

import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import haoshi.com.shop.bean.chat.dao.ChatBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.dao.ChatFriendBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class ChatFriendsImpl extends ChatBaseBean<ChatFriendBean, ChatFriendBeanDao> {
    public static ChatFriendsImpl instance;

    @Override
    public ChatFriendBean select(String id) {
        return getDao().queryBuilder().where(ChatFriendBeanDao.Properties.Fid.eq(id)).build().unique();
    }

    public static ChatFriendsImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new ChatFriendsImpl(dbName);
        }
        return instance;
    }

    public static ChatFriendsImpl getInstance() {
        return getInstance("");
    }

    public void insertAll(ArrayList<ChatFriendBean> data) {
        for (ChatFriendBean f : data) {
            ChatFriendBean a = select(f.getFid());
            if (a == null) {
                add(f);
            } else {
                update(f);
            }
        }
    }

    public ChatFriendsImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getChatFriendBeanDao());
    }

    public void delete(String fid){
        getDao().queryBuilder().where(ChatFriendBeanDao.Properties.Fid.eq(fid)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    private int type;

    public ChatFriendsImpl setType(int type) {
        this.type = type;
        return instance;
    }

    public void deleteAll(int type) {
        setType(type);
        for (ChatFriendBean f : getAll()) {
            delete(f);
        }
    }

    @Override
    protected List<ChatFriendBean> getAll() {
        QueryBuilder<ChatFriendBean> queryBuilder = getDao().queryBuilder();
        return queryBuilder.where(ChatFriendBeanDao.Properties.Type.eq(type)).build().list();
    }

    public void add(ChatMessageBean bean) {
        boolean isU = TextUtils.isEmpty(bean.getTouid());
        String id = isU ? bean.getGroupid() : bean.getTouid();
        ChatFriendBean f = select(id);
        if (f == null) {
            f = new ChatFriendBean();
            f.setType(isU ? 1 : 2);
            f.setFid(id);
            f.setHeader(bean.getLogo());
            f.setName(bean.getName());
            f.setGid("-1");
            add(f);
        } else {
            f.setHeader(bean.getLogo());
            f.setName(bean.getName());
            update(f);
        }

    }


}
