package client.bean.chat.dao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

@Entity
public class ChatMessageBean {
    @Id
    private Long id;
    @Property(nameInDb = "UID")
    private String uid;
    @Property(nameInDb = "HEADURL")
    private String headUrl;
    @Property(nameInDb = "NICKNAME")
    private String nickName;
    @Property(nameInDb = "CONTENT")
    private String content;
    @Property(nameInDb = "TIME")
    private Long time;
    @Property(nameInDb = "FID")
    @Unique
    private String fId;
    public String getFId() {
        return this.fId;
    }
    public void setFId(String fId) {
        this.fId = fId;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getHeadUrl() {
        return this.headUrl;
    }
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 624578510)
    public ChatMessageBean(Long id, String uid, String headUrl, String nickName,
            String content, Long time, String fId) {
        this.id = id;
        this.uid = uid;
        this.headUrl = headUrl;
        this.nickName = nickName;
        this.content = content;
        this.time = time;
        this.fId = fId;
    }
    @Generated(hash = 1557449535)
    public ChatMessageBean() {
    }

    

}