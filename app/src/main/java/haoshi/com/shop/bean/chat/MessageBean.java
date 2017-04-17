package haoshi.com.shop.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import haoshi.com.shop.bean.chat.dao.DaoSession;
import haoshi.com.shop.bean.chat.dao.ChatFriendBeanDao;

/**
 * Created by dengmingzhi on 2017/3/28.
 */

@Entity
public class MessageBean {
    @Id
    private String id;
    private long time;
    private int type;
    private int num;
    private int from;//1、对方发给我2、我发给对方
    @Unique
    private String sign;
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public int getFrom() {
        return this.from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Generated(hash = 1935811510)
    public MessageBean(String id, long time, int type, int num, int from,
            String sign) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.num = num;
        this.from = from;
        this.sign = sign;
    }
    @Generated(hash = 1588632019)
    public MessageBean() {
    }
}
