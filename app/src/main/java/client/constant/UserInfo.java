package client.constant;

import android.content.Context;

import java.util.Map;

import client.bean.login.LoginBean;
import util.ContextUtil;
import util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2017/3/14.
 * 登录信息
 */

public class UserInfo {
    public static String token;
    public static String userId;
    public static String userName;
    public static String userPhone;

    public static void saveUserInfo(LoginBean bean) {
        token = bean.data.token;
        userId = bean.data.userId;
        userName = bean.data.userName;
        userPhone = bean.data.userPhone;
        new SharedPreferenUtil(ContextUtil.getCtx(), "loginInfo").setData(new String[]{"uid", bean.data.userId, "token", bean.data.token, "name", bean.data.userName, "phone", bean.data.userPhone});
    }

    public static void getUserInfo() {
        Map<String, String> loginInfo = new SharedPreferenUtil(ContextUtil.getCtx(), "loginInfo").getData(new String[]{"uid", "token", "name", "phone"});
        if (loginInfo != null && loginInfo.size() == 4) {
            userId = loginInfo.get("uid");
            token = loginInfo.get("token");
            userName = loginInfo.get("name");
            userPhone = loginInfo.get("phone");
        }
    }
}
