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
public class TextBean {
    @Id
    private String sign;
    private String content;
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
    @Generated(hash = 2074435289)
    public TextBean(String sign, String content) {
        this.sign = sign;
        this.content = content;
    }
    @Generated(hash = 809912491)
    public TextBean() {
    }
}
