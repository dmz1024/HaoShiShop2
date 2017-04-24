package haoshi.com.shop.activity;

import com.tencent.bugly.Bugly;

import base.activity.MyApplication;

/**
 * Created by dengmingzhi on 2017/1/16.
 */

public class MyApp extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), "010f081132", false);
    }
}
