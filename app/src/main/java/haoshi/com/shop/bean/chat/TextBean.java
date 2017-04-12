package haoshi.com.shop.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengmingzhi on 2017/2/24.
 */
@Entity
public class TextBean {
    @Id
    private String sign;
    private String content;
    private int status;//1发送中2发送成功3发送失败
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    @Generated(hash = 1886041674)
    public TextBean(String sign, String content, int status) {
        this.sign = sign;
        this.content = content;
        this.status = status;
    }
    @Generated(hash = 809912491)
    public TextBean() {
    }
    
}
