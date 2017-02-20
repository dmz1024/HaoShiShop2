package base.activity;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.cache.DBCacheStore;

import util.JLogUtils;
import util.Util;

/**
 * Created by dengmingzhi on 2016/11/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNoHttp();
        JLogUtils.setDebug(true);
        Util.setApplication(this);
        Fresco.initialize(this);
    }

    //网络框架初始化
    private void initNoHttp() {
        //
        NoHttp.initialize(this, new NoHttp.Config()
                // 设置全局连接超时时间，单位毫秒
                .setConnectTimeout(10 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒
                .setReadTimeout(10 * 1000)
                // 保存到数据库，如果不使用缓存，设置false禁用。
                .setCacheStore(new DBCacheStore(this).setEnable(false))
                // 使用OkHttp
                .setNetworkExecutor(new OkHttpNetworkExecutor()));


        // 或者保存到SD卡
        //.setCacheStore(
        //   new DiskCacheStore(this)
        //        )
        // 默认保存数据库DBCookieStore，开发者可以自己实现。
//        .setCookieStore(
//                new DBCookieStore(this).setEnable(false) // 如果不维护cookie，设置false禁用。
//        )

//        Logger.setDebug(true);
//        Logger.setTag("NoHttp数据");
    }


}
