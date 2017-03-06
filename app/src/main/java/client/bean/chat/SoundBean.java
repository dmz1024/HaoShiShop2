package client.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengmingzhi on 2017/2/24.
 */
@Entity
public class SoundBean {
    @Id
    private String sign;
    private String filepath;
    private int time;
    private int status;
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getFilepath() {
        return this.filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    @Generated(hash = 1170882047)
    public SoundBean(String sign, String filepath, int time, int status) {
        this.sign = sign;
        this.filepath = filepath;
        this.time = time;
        this.status = status;
    }
    @Generated(hash = 93261289)
    public SoundBean() {
    }
}
