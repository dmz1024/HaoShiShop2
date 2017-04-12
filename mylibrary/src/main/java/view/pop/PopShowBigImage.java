package view.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mall.naiqiao.mylibrary.R;

import base.other.PopBaseView;

/**
 * Created by dengmingzhi on 2017/3/29.
 */

public class PopShowBigImage extends PopBaseView {
    public PopShowBigImage(Context ctx) {
        super(ctx);
    }

    private String url;

    public PopShowBigImage(Context ctx, String url) {
        super(ctx);
        this.url = url;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_show_big_image, null);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        Glide.with(ctx).load(url).into(iv_img);
        return view;
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getAnimation() {
        return 0;
    }

    @Override
    protected float getAlpha() {
        return 0f;
    }
}
