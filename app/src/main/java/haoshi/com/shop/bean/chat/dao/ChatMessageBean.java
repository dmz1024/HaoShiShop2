package haoshi.com.shop.bean.chat.dao;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class ChatMessageBean {
    public String from_client_name;
    public int filetype;
    public long time;
    public String ID;
    public String uid;
    public String touid;
    public String groupid;
    public String content;
    public String file;
    public String extend;
    public String type;
    public long createtime;
    public String name;
    public String logo;
    public int nums;

    public String groupname;
    public String grouplogo;
    public String linkurl;


    /**
     * 是否是群组
     *
     * @return
     */
    public boolean isGroup() {
        return TextUtils.isEmpty(touid);
    }

    /**
     * 用户id
     *
     * @return
     */
    public String getId() {
        return isGroup() ? groupid : uid;
    }


    public String getType() {
        return type;
    }

    public long getCreatetime() {
        return createtime * 1000;
    }

    public long getTime() {
        return time * 1000;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getFrom_client_name() {
        return from_client_name;
    }

    public String getLogoOnLine() {
        return isGroup() ? grouplogo : logo;
    }

    public String getGrouplogo() {
        return grouplogo;
    }

    public int getIntType() {
        return Integer.parseInt(type);
    }

    public String getUid() {
        return uid;
    }


    public String getContent() {
        return content;
    }

    public String getFile() {
        return file;
    }

    public String getExtend() {
        return extend;
    }

    public String getNameOnLine() {
        return isGroup() ? groupname : from_client_name;
    }

    public int getFiletype() {
        return filetype;
    }
}