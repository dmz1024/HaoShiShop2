package haoshi.com.shop.bean.chat;

import android.text.TextUtils;

/**
 * Created by dengmingzhi on 2017/3/21.
 */

/**
 * jieshu
 */
public class ReceiveChatBean {
    public String type;
    public String uid;
    public String touid;
    public String groupid;
    public String groupname;
    public String grouplogo;
    public String name;
    public String from_client_name;
    public String file;
    public String logo;
    public String linkurl;
    public String content;
    public long time;
    public int filetype;
    public String extend;

    public String getName() {
        return TextUtils.isEmpty(touid) ? groupname : from_client_name;
    }

    public String getId() {
        return TextUtils.isEmpty(touid) ? groupid : touid;
    }

    public long getTime() {
        return time * 1000;
    }

}
