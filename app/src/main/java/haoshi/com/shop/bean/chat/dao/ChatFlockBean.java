package haoshi.com.shop.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengmingzhi on 2017/3/27.
 */

@Entity
public class ChatFlockBean {
    @Id
    private String groupid;
    private String grouplogo;
    private String groupname;
    private String nums;
    public String getNums() {
        return this.nums;
    }
    public void setNums(String nums) {
        this.nums = nums;
    }
    public String getGroupname() {
        return this.groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    public String getGrouplogo() {
        return this.grouplogo;
    }
    public void setGrouplogo(String grouplogo) {
        this.grouplogo = grouplogo;
    }
    public String getGroupid() {
        return this.groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    @Generated(hash = 804533295)
    public ChatFlockBean(String groupid, String grouplogo, String groupname,
            String nums) {
        this.groupid = groupid;
        this.grouplogo = grouplogo;
        this.groupname = groupname;
        this.nums = nums;
    }
    @Generated(hash = 2048581093)
    public ChatFlockBean() {
    }
    
}
