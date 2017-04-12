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
    private int type;
    private long time;
    private int isRead;//0读了1未读
    private int status;//0发送中1发送成功2发送失败
    private int from;//1、对方发给我2、我发给对方
    @ToOne(joinProperty = "sign")
    private SoundBean soundBean;
    @ToOne(joinProperty = "sign")
    private TextBean textBean;
    @ToOne(joinProperty = "sign")
    private FileBean fileBean;
    @ToOne(joinProperty = "sign")
    private PhotoBean photoBean;
    @ToOne(joinProperty = "fid")
    private ChatFriendBean fUser;
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 658241439)
    public void setFUser(ChatFriendBean fUser) {
        synchronized (this) {
            this.fUser = fUser;
            fid = fUser == null ? null : fUser.getFid();
            fUser__resolvedKey = fid;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 954843192)
    public ChatFriendBean getFUser() {
        String __key = this.fid;
        if (fUser__resolvedKey == null || fUser__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChatFriendBeanDao targetDao = daoSession.getChatFriendBeanDao();
            ChatFriendBean fUserNew = targetDao.load(__key);
            synchronized (this) {
                fUser = fUserNew;
                fUser__resolvedKey = __key;
            }
        }
        return fUser;
    }
    @Generated(hash = 599643429)
    private transient String fUser__resolvedKey;
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1534596719)
    public void setPhotoBean(PhotoBean photoBean) {
        synchronized (this) {
            this.photoBean = photoBean;
            sign = photoBean == null ? null : photoBean.getSign();
            photoBean__resolvedKey = sign;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1676156330)
    public PhotoBean getPhotoBean() {
        String __key = this.sign;
        if (photoBean__resolvedKey == null || photoBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhotoBeanDao targetDao = daoSession.getPhotoBeanDao();
            PhotoBean photoBeanNew = targetDao.load(__key);
            synchronized (this) {
                photoBean = photoBeanNew;
                photoBean__resolvedKey = __key;
            }
        }
        return photoBean;
    }
    @Generated(hash = 391009790)
    private transient String photoBean__resolvedKey;
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 625789170)
    public void setFileBean(FileBean fileBean) {
        synchronized (this) {
            this.fileBean = fileBean;
            sign = fileBean == null ? null : fileBean.getSign();
            fileBean__resolvedKey = sign;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 246753219)
    public FileBean getFileBean() {
        String __key = this.sign;
        if (fileBean__resolvedKey == null || fileBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FileBeanDao targetDao = daoSession.getFileBeanDao();
            FileBean fileBeanNew = targetDao.load(__key);
            synchronized (this) {
                fileBean = fileBeanNew;
                fileBean__resolvedKey = __key;
            }
        }
        return fileBean;
    }
    @Generated(hash = 1151969240)
    private transient String fileBean__resolvedKey;
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1550674832)
    public void setTextBean(TextBean textBean) {
        synchronized (this) {
            this.textBean = textBean;
            sign = textBean == null ? null : textBean.getSign();
            textBean__resolvedKey = sign;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1131206420)
    public TextBean getTextBean() {
        String __key = this.sign;
        if (textBean__resolvedKey == null || textBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TextBeanDao targetDao = daoSession.getTextBeanDao();
            TextBean textBeanNew = targetDao.load(__key);
            synchronized (this) {
                textBean = textBeanNew;
                textBean__resolvedKey = __key;
            }
        }
        return textBean;
    }
    @Generated(hash = 79100044)
    private transient String textBean__resolvedKey;
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1762222194)
    public void setSoundBean(SoundBean soundBean) {
        synchronized (this) {
            this.soundBean = soundBean;
            sign = soundBean == null ? null : soundBean.getSign();
            soundBean__resolvedKey = sign;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1254351699)
    public SoundBean getSoundBean() {
        String __key = this.sign;
        if (soundBean__resolvedKey == null || soundBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SoundBeanDao targetDao = daoSession.getSoundBeanDao();
            SoundBean soundBeanNew = targetDao.load(__key);
            synchronized (this) {
                soundBean = soundBeanNew;
                soundBean__resolvedKey = __key;
            }
        }
        return soundBean;
    }
    @Generated(hash = 1012461589)
    private transient String soundBean__resolvedKey;
    /** Used for active entity operations. */
    @Generated(hash = 2077822996)
    private transient ChatViewBeanDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1310006873)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChatViewBeanDao() : null;
    }
    @Generated(hash = 304419200)
    public ChatViewBean(String sign, String fid, int type, long time, int isRead,
            int status, int from) {
        this.sign = sign;
        this.fid = fid;
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
