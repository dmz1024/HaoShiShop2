package haoshi.com.shop.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by dengmingzhi on 2017/3/3.
 */

@Entity
public class ChatFriendGroupBean {
    @Id
    private String ID;//分组id

    private String uid;//用户id

    private String name;//组名称

    private int number;//分组人数
    @ToMany(referencedJoinProperty = "gid")
    private List<ChatFriendBean> fbs;
    @Transient
    private ArrayList<ChatFriendBean> friends;

    @Transient
    private boolean isShow;

    /** Used for active entity operations. */
    @Generated(hash = 1830010625)
    private transient ChatFriendGroupBeanDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    @Generated(hash = 852501838)
    public ChatFriendGroupBean(String ID, String uid, String name, int number) {
        this.ID = ID;
        this.uid = uid;
        this.name = name;
        this.number = number;
    }

    @Generated(hash = 948431386)
    public ChatFriendGroupBean() {
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public ArrayList<ChatFriendBean> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<ChatFriendBean> friends) {
        this.friends = friends;
    }

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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1715778137)
    public synchronized void resetFbs() {
        fbs = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1872589917)
    public List<ChatFriendBean> getFbs() {
        if (fbs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChatFriendBeanDao targetDao = daoSession.getChatFriendBeanDao();
            List<ChatFriendBean> fbsNew = targetDao._queryChatFriendGroupBean_Fbs(ID);
            synchronized (this) {
                if(fbs == null) {
                    fbs = fbsNew;
                }
            }
        }
        return fbs;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 184987862)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChatFriendGroupBeanDao() : null;
    }

    
}
