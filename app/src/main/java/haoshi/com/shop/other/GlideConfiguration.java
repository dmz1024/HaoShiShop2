package haoshi.com.shop.other;

/**
 * Created by dengmingzhi on 2016/11/9.
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import util.FileUtil;

/**
 * Created by zhaoyong on 2016/6/16.
 * Glide配置文件
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //自定义缓存目录，磁盘缓存给150M
        builder.setDiskCache(new DiskLruCacheFactory(FileUtil.getRootPath(context).getAbsolutePath(), "haoshi_glide", 150 * 1024 * 1024));
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}