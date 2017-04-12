package haoshi.com.shop.bean.chat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by dengmingzhi on 2017/2/24.
 */

@Entity
public class SendBean {
    @Id
    private String sign;
    private String desc;
    private String name;
    private String id;
    private String img;
    private boolean isGoods;
    private int status;//1发送中2发送成功3发送失败
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public boolean getIsGoods() {
        return this.isGoods;
    }
    public void setIsGoods(boolean isGoods) {
        this.isGoods = isGoods;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    @Generated(hash = 1308011597)
    public SendBean(String sign, String desc, String name, String id, String img,
            boolean isGoods, int status) {
        this.sign = sign;
        this.desc = desc;
        this.name = name;
        this.id = id;
        this.img = img;
        this.isGoods = isGoods;
        this.status = status;
    }
    @Generated(hash = 1036445372)
    public SendBean() {
    }
}
