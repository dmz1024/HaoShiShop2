package haoshi.com.shop.bean.chat.impl;

import android.text.TextUtils;

import java.util.List;

import haoshi.com.shop.bean.chat.MessageBean;
import haoshi.com.shop.bean.chat.MessageBeanDao;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;


/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class MessagesImpl extends ChatBaseBean<MessageBean, MessageBeanDao> {
    public static MessagesImpl instance;

    public static MessagesImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new MessagesImpl(dbName);
        }
        return instance;
    }

    public static MessagesImpl getInstance() {
        return getInstance("");
    }


    public MessagesImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getMessageBeanDao());
    }

    @Override
    public MessageBean select(String id) {
        return getDao().queryBuilder().where(MessageBeanDao.Properties.Id.eq(id)).build().unique();
    }


    @Override
    protected List<MessageBean> getAll() {
        return getDao().queryBuilder().orderDesc(MessageBeanDao.Properties.Time).build().list();
    }

    public void add(ChatMessageBean chat, String sign) {
        String id = chat.getId();
        MessageBean bean = select(id);
        if (bean != null) {
            delete(bean);
        }
        bean = new MessageBean();
        bean.setType(chat.getIntType());
        bean.setFrom(1);
        bean.setId(id);
        bean.setNum(10);
        bean.setSign(sign);
        bean.setTime(chat.getCreatetime());
        add(bean);
    }

    public void addOnLine(ChatMessageBean chat, String sign) {
        String id = chat.getId();
        MessageBean bean = select(id);
        if (bean != null) {
            delete(bean);
        }
        bean = new MessageBean();
        bean.setType(chat.getFiletype());
        bean.setFrom(1);
        bean.setId(id);
        bean.setNum(10);
        bean.setSign(sign);
        bean.setTime(chat.getTime());
        add(bean);
    }

    public void addM(MessageBean bean) {
        MessageBean select = select(bean.getId());
        if (select != null) {
            update(bean);
        } else {
            add(bean);
        }
    }
}
