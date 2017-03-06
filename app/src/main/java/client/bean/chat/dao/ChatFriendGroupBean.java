package client.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

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
    private String gid;//分组id

    private String uid;//用户id

    private String name;//组名称
    @ToMany(referencedJoinProperty="gid")
    private List<ChatFriendBean> fbs;
    @Transient
    private int count=1;//分组下好友数量

    @Transient
    private ArrayList<ChatFriendBean> fds;
    @Transient
    private boolean isShow;

    /** Used for active entity operations. */
    @Generated(hash = 1830010625)
    private transient ChatFriendGroupBeanDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
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
    public String getGid() {
        return this.gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }

    @Generated(hash = 1449896052)
    public ChatFriendGroupBean(String gid, String uid, String name) {
        this.gid = gid;
        this.uid = uid;
        this.name = name;
    }

    @Generated(hash = 948431386)
    public ChatFriendGroupBean() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ChatFriendBean> getFds() {
        return fds;
    }

    public void setFds(ArrayList<ChatFriendBean> fds) {
        this.fds = fds;
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
    @Generated(hash = 640174860)
    public List<ChatFriendBean> getFbs() {
        if (fbs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChatFriendBeanDao targetDao = daoSession.getChatFriendBeanDao();
            List<ChatFriendBean> fbsNew = targetDao._queryChatFriendGroupBean_Fbs(gid);
            synchronized (this) {
                if(fbs == null) {
                    fbs = fbsNew;
                }
            }
        }
        return fbs;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 184987862)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChatFriendGroupBeanDao() : null;
    }



}
