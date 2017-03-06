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
public class PhotoBean {
    @Id
    private String sign;
    private String url_path;
    private int w;
    private int h;
    public int getH() {
        return this.h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public int getW() {
        return this.w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public String getUrl_path() {
        return this.url_path;
    }
    public void setUrl_path(String url_path) {
        this.url_path = url_path;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    @Generated(hash = 1471429052)
    public PhotoBean(String sign, String url_path, int w, int h) {
        this.sign = sign;
        this.url_path = url_path;
        this.w = w;
        this.h = h;
    }
    @Generated(hash = 487180461)
    public PhotoBean() {
    }
}
