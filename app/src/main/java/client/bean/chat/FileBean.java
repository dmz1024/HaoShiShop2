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
public class FileBean {
    @Id
    private String sign;
    private String filepath;
    private String status;
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    @Generated(hash = 1512724471)
    public FileBean(String sign, String filepath, String status) {
        this.sign = sign;
        this.filepath = filepath;
        this.status = status;
    }
    @Generated(hash = 1910776192)
    public FileBean() {
    }
}
