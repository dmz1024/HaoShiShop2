package haoshi.com.shop.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
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
    private int status;//1发送中2发送成功3发送失败
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
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
    @Generated(hash = 541465142)
    public PhotoBean(String sign, String url_path, int w, int h, int status) {
        this.sign = sign;
        this.url_path = url_path;
        this.w = w;
        this.h = h;
        this.status = status;
    }
    @Generated(hash = 487180461)
    public PhotoBean() {
    }
}
