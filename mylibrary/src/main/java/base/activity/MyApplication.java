package base.activity;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.tencent.bugly.Bugly;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.cache.DBCacheStore;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
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
        initImageLoader(getApplicationContext());
//        Bugly.init(getApplicationContext(), "010f081132", false);

        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(true)
                // 在debug=false时，即线上环境时，上述异常会被捕获并回调ExceptionHandler
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
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


    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();//不会在内存中缓存多个大小的图片
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());//为了保证图片名称唯一
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        //内存缓存大小默认是：app可用内存的1/8
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


}
