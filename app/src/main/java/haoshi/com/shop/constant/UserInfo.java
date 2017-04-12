package haoshi.com.shop.constant;

import android.util.Log;

import java.util.Map;

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
            Log.d("用户信息", userId + "--" + token + "--" + userName + "--" + userPhone);
        }
    }

    public static void clearUserInfo() {
        new SharedPreferenUtil(ContextUtil.getCtx(), "loginInfo").setData(new String[]{"uid", "", "token", "", "name", "", "phone", ""});
    }

    public static String getSign(String fid) {
        return  Util.MD5(fid + UserInfo.userId + System.currentTimeMillis() + "android1.0.0");
    }
}
