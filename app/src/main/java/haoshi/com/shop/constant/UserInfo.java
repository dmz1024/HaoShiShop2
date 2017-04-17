package haoshi.com.shop.constant;

import android.util.Log;

import java.util.Map;

import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.bean.login.LoginBean;
import util.ContextUtil;
import util.SharedPreferenUtil;
import util.Util;

/**
 * Created by dengmingzhi on 2017/3/14.
 * 登录信息
 */

public class UserInfo {
    public static String token;
    public static String userId;
    public static String userName;
    public static String userPhone;
    public static String logo;

    public static void saveUserInfo(LoginBean bean) {
        token = bean.data.token;
        userId = bean.data.userId;
        userName = bean.data.userName;
        userPhone = bean.data.userPhone;
        logo = bean.data.userPhoto;
        new SharedPreferenUtil(ContextUtil.getCtx(), "loginInfo").setData(new String[]{"uid", bean.data.userId, "token", bean.data.token, "name", bean.data.userName, "phone", bean.data.userPhone, "logo", bean.data.userPhoto});
        ChatFriendBean friendBean = new ChatFriendBean();
        friendBean.setFid(userId);
        friendBean.setType(2);
        friendBean.setGid("-1");
        friendBean.setLogo(logo);
        friendBean.setName(userName);
        ChatFriendBean select = ChatFriendsImpl.getInstance().select(userId);
        if (select != null) {
            ChatFriendsImpl.getInstance().delete(select);
        }
        ChatFriendsImpl.getInstance().add(friendBean);
    }

    public static void getUserInfo() {
        Map<String, String> loginInfo = new SharedPreferenUtil(ContextUtil.getCtx(), "loginInfo").getData(new String[]{"uid", "token", "name", "phone", "logo"});
        if (loginInfo != null && loginInfo.size() == 5) {
            userId = loginInfo.get("uid");
            token = loginInfo.get("token");
            userName = loginInfo.get("name");
            userPhone = loginInfo.get("phone");
            logo = loginInfo.get("logo");
            Log.d("用户信息", userId + "--" + token + "--" + userName + "--" + userPhone + "---" + logo);
            ChatFriendBean friendBean = new ChatFriendBean();
            friendBean.setFid(userId);
            friendBean.setType(2);
            friendBean.setGid("-1");
            friendBean.setLogo(logo);
            friendBean.setName(userName);
            ChatFriendBean select = ChatFriendsImpl.getInstance().select(userId);
            if (select != null) {
                ChatFriendsImpl.getInstance().delete(select);
            }
            ChatFriendsImpl.getInstance().add(friendBean);

        }

    }

    public static void clearUserInfo() {
        new SharedPreferenUtil(ContextUtil.getCtx(), "loginInfo").setData(new String[]{"uid", "", "token", "", "name", "", "phone", "", "logo", ""});
    }

    public static String getSign(String fid) {
        return Util.MD5(fid + UserInfo.userId + System.currentTimeMillis() + "android1.0.0");
    }


    public static void addUser(String... content) {

        ChatFriendBean select = ChatFriendsImpl.getInstance().select(content[0]);
        if (select == null) {
            select = new ChatFriendBean();
            select.setFid(content[0]);
            select.setType(2);
            select.setGid("-1");
            select.setLogo(content[1]);
            select.setName(content[2]);
            ChatFriendsImpl.getInstance().add(select);
        } else {
            select.setLogo(content[1]);
            select.setName(content[2]);
            ChatFriendsImpl.getInstance().update(select);
        }

    }
}
