package haoshi.com.shop.bean.chat.impl;

import android.text.TextUtils;

import java.util.List;

import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;
import haoshi.com.shop.bean.chat.dao.ChatBaseBean;
import haoshi.com.shop.bean.chat.dao.ChatMessageBean;
import haoshi.com.shop.bean.chat.dao.ChatViewBean;
import haoshi.com.shop.bean.chat.dao.ChatViewBeanDao;
import haoshi.com.shop.constant.UserInfo;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChatViewsImpl extends ChatBaseBean<ChatViewBean, ChatViewBeanDao> {
    private String fid;
    private static ChatViewsImpl instance;

    public ChatViewsImpl(String dbName) {
        super(dbName);
        setDao(getDaoSession().getChatViewBeanDao());
    }

    public static ChatViewsImpl getInstance() {
        return getInstance("");
    }

    public static ChatViewsImpl getInstance(String dbName) {
        if (instance == null) {
            instance = new ChatViewsImpl(dbName);
        }
        return instance;
    }

    public ChatViewsImpl setFid(String fid) {
        this.fid = fid;
        return instance;
    }


    @Override
    protected List<ChatViewBean> getAll() {
        getDao().detachAll();
        return getDao().queryBuilder()
                .where(ChatViewBeanDao.Properties.Fid.eq(fid))
                .build().list();
    }

    public String add(ChatMessageBean bean) {
        ChatFriendsImpl.getInstance().add(bean);
        ChatViewBean c = new ChatViewBean();
        String sign = UserInfo.getSign(bean.getUid());
        c.setSign(sign);
        boolean isU = TextUtils.isEmpty(bean.getTouid());
        String id = isU ? bean.getGroupid() : bean.getUid();
        c.setFid(id);
        c.setType(bean.getType());
        c.setTime(bean.getCreatetime()*1000);
        c.setIsRead(1);
        c.setStatus(0);
        c.setFrom(1);
        switch (bean.getType()) {
            case 1:
                TextBean tb = new TextBean(sign, bean.getContent(), 2);
                TextImpl.getInstance().add(tb);
                break;
            case 2:
                SoundBean sb = new SoundBean(sign, bean.getFile(), Integer.parseInt(bean.getExtend()), 2, 2);
                SoundImpl.getInstance().add(sb);
                break;
            case 3:
                String[] pbs = bean.getExtend().split(",");
                PhotoBean pb = new PhotoBean(sign, bean.getFile(), Integer.parseInt(pbs[0]), Integer.parseInt(pbs[1]), 2);
                PhotoImpl.getInstance().add(pb);
                break;
            case 4:
                String[] fbs = bean.getExtend().split(",");
                FileBean fb = new FileBean(sign, bean.getFile(), 1, 2, fbs[0], fbs[2], fbs[1]);
                FileImpl.getInstance().add(fb);
                break;
            case 5:
                break;
        }

        add(c);
        return sign;
    }


}
