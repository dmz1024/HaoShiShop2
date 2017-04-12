package haoshi.com.shop.bean.chat.impl;


import haoshi.com.shop.bean.chat.dao.ChatBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatFlockBean;
import haoshi.com.shop.bean.chat.dao.ChatFlockBeanDao;


/**
 * Created by dengmingzhi on 2017/3/27.
 */

public class ChatFlocksImpl extends ChatBaseBean<ChatFlockBean, ChatFlockBeanDao> {
    public static ChatFlocksImpl instance;

    public static ChatFlocksImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new ChatFlocksImpl(dbName);
        }
        return instance;
    }

    public static ChatFlocksImpl getInstance() {
        return getInstance("");
    }



    public ChatFlocksImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getChatFlockBeanDao());
    }

}
