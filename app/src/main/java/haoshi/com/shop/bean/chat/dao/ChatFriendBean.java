package haoshi.com.shop.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengmingzhi on 2017/3/2.
 * 好友
 */

@Entity
public class ChatFriendBean {

    @Id
    private String fid;
    private String name;
    private String header;
    private String gid;

    private int type;//1群组、2好友,3、店铺用来区分是群组还是好友

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

    public String getFid() {
        return this.fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @Generated(hash = 728430091)
    public ChatFriendBean(String fid, String name, String header, String gid,
            int type) {
        this.fid = fid;
        this.name = name;
        this.header = header;
        this.gid = gid;
        this.type = type;
    }

    @Generated(hash = 665039583)
    public ChatFriendBean() {
    }

    

}
