package util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by dengmingzhi on 16/10/13.
 */

public class GlideUtil {
    public static int errImage;
    public static int loadImage;

    public static void GlideErrAndOc(Context ctx, Object url, ImageView iv) {
        Glide.with(ctx).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(errImage).placeholder(loadImage).into(iv);
//        Glide.with(ctx).load(url).skipMemoryCache(true).error(errImage).placeholder(loadImage).into(iv);

    }

    public static void setErrImage(int errImage) {
        GlideUtil.errImage = errImage;
    }

    public static void setLoadImage(int loadImage) {
        GlideUtil.loadImage = loadImage;
    }
}
