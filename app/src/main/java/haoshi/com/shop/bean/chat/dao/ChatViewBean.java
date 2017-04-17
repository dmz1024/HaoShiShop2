package haoshi.com.shop.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import haoshi.com.shop.bean.chat.FileBean;
import haoshi.com.shop.bean.chat.PhotoBean;
import haoshi.com.shop.bean.chat.SoundBean;
import haoshi.com.shop.bean.chat.TextBean;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import haoshi.com.shop.bean.chat.SoundBeanDao;
import haoshi.com.shop.bean.chat.TextBeanDao;
import haoshi.com.shop.bean.chat.FileBeanDao;
import haoshi.com.shop.bean.chat.PhotoBeanDao;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

@Entity
public class ChatViewBean {
    @Id
    private String sign;
    private String fid;
    private String fromId;
    private String uid;
    private int type;
    private long time;
    private int isRead;//0读了1未读
    private int status;//0发送中1发送成功2发送失败
    private int from;//1、对方发给我2、我发给对方
    public int getFrom() {
        return this.from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getIsRead() {
        return this.isRead;
    }
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getFromId() {
        return this.fromId;
    }
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
    public String getFid() {
        return this.fid;
    }
    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    @Generated(hash = 1811451132)
    public ChatViewBean(String sign, String fid, String fromId, String uid,
            int type, long time, int isRead, int status, int from) {
        this.sign = sign;
        this.fid = fid;
        this.fromId = fromId;
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.isRead = isRead;
        this.status = status;
        this.from = from;
    }
    @Generated(hash = 81043527)
    public ChatViewBean() {
    }
}
