package client.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by dengmingzhi on 2017/3/2.
 * 好友
 */

@Entity
public class ChatFriendBean {

    @Id
    private String uid;

    private String name;

    private String header;

    private String nickname;

    private String gid;

    private int type;//用来区分是群组还是好友

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGid() {
        return this.gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    @Generated(hash = 957699929)
    public ChatFriendBean(String uid, String name, String header, String nickname,
            String gid, int type) {
        this.uid = uid;
        this.name = name;
        this.header = header;
        this.nickname = nickname;
        this.gid = gid;
        this.type = type;
    }

    @Generated(hash = 665039583)
    public ChatFriendBean() {
    }

}
