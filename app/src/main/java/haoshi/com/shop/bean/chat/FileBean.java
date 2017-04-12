package haoshi.com.shop.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengmingzhi on 2017/2/24.
 */

@Entity
public class FileBean {
    @Id
    private String sign;
    private String filepath;
    private int isLoad;//0已下载1未下载2下载中3下0已下载1未下载2下载中3下载失败载失败
    private int status;//1发送中2发送成功3发送失败
    private String fileType;//文件类型
    private String fileName;//文件名
    private String size;//文件大小
    public String getSize() {
        return this.size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileType() {
        return this.fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getIsLoad() {
        return this.isLoad;
    }
    public void setIsLoad(int isLoad) {
        this.isLoad = isLoad;
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
    @Generated(hash = 1012184099)
    public FileBean(String sign, String filepath, int isLoad, int status,
            String fileType, String fileName, String size) {
        this.sign = sign;
        this.filepath = filepath;
        this.isLoad = isLoad;
        this.status = status;
        this.fileType = fileType;
        this.fileName = fileName;
        this.size = size;
    }
    @Generated(hash = 1910776192)
    public FileBean() {
    }
}
