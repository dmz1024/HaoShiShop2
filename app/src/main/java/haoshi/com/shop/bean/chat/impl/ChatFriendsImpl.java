package haoshi.com.shop.bean.chat.impl;

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

    public void delete(String fid) {
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

    /**
     * 这是添加从未接收消息记录的好友信息
     *
     * @param bean
     */
    public void add(ChatMessageBean bean) {
        String id = bean.getId();
        ChatFriendBean f = select(id);
        if (f == null) {
            f = new ChatFriendBean();
            f.setType(bean.isGroup() ? 1 : 2);//1是群组2是好友
            f.setFid(id);
            f.setLogo(bean.getLogo());
            f.setName(bean.getName());
            f.setGid("-1");
            add(f);
        } else {
            f.setLogo(bean.getLogo());
            f.setName(bean.getName());
            update(f);
        }

    }


    /**
     * 这是从在线消息
     *
     * @param bean
     */
    public void addOnline(ChatMessageBean bean) {
        String id = bean.getId();
        ChatFriendBean f = select(id);
        if (bean.isGroup()) {
            if (f == null) {
                f = new ChatFriendBean();
                f.setType(1);//1是群组2是好友
                f.setFid(id);
                f.setLogo(bean.getGrouplogo());
                f.setName(bean.getNameOnLine());
                f.setGid("-1");
                add(f);
            } else {
                f.setLogo(bean.getGrouplogo());
                f.setName(bean.getNameOnLine());
                update(f);
            }

            String uId = bean.getUid();
            ChatFriendBean g = select(uId);
            if (g == null) {
                g = new ChatFriendBean();
                g.setType(2);//1是群组2是好友
                g.setFid(uId);
                g.setLogo(bean.getLogo());
                g.setName(bean.getFrom_client_name());
                g.setGid("-1");
                add(g);
            } else {
                g.setLogo(bean.getLogo());
                g.setName(bean.getFrom_client_name());
                update(g);
            }

        } else {
            if (f == null) {
                f = new ChatFriendBean();
                f.setType(2);//1是群组2是好友
                f.setFid(id);
                f.setLogo(bean.getLogo());
                f.setName(bean.getFrom_client_name());
                f.setGid("-1");
                add(f);
            } else {
                f.setLogo(bean.getLogo());
                f.setName(bean.getFrom_client_name());
                update(f);
            }

        }


    }


}
