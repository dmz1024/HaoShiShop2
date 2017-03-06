package client.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengmingzhi on 2017/3/1.
 */
@Entity
public class TestBean {
    @Id
    private Long id;
    @Property(nameInDb = "CONTENT")
    private String content;
    @Property(nameInDb = "TIME")
    private String time;
    @Property(nameInDb = "UID")
    private String uid;
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 926092310)
    public TestBean(Long id, String content, String time, String uid) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.uid = uid;
    }
    @Generated(hash = 2087637710)
    public TestBean() {
    }
}
