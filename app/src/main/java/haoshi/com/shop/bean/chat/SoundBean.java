package haoshi.com.shop.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
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
    private int isSound;//1、听2、未听
    private int status;//1发送中2发送成功3发送失败
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getIsSound() {
        return this.isSound;
    }
    public void setIsSound(int isSound) {
        this.isSound = isSound;
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
    @Generated(hash = 113422082)
    public SoundBean(String sign, String filepath, int time, int isSound, int status) {
        this.sign = sign;
        this.filepath = filepath;
        this.time = time;
        this.isSound = isSound;
        this.status = status;
    }
    @Generated(hash = 93261289)
    public SoundBean() {
    }
    
}
