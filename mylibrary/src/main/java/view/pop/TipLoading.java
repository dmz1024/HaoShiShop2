package view.pop;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import base.other.PopBaseView;
import util.Util;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public class TipLoading extends PopBaseView {
    private ImageView iv_img;
    private TextView tv_content;

    public TipLoading(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.tip_loading, null);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        startAnimation();
        return view;
    }

    @Override
    protected float getAlpha() {
        return 0.7f;
    }

    @Override
    protected long getShowTime() {
        return 100;
    }

    @Override
    protected long getDismissTime() {
        return 300;
    }

    public void startAnimation() {
        Animation operatingAnim = AnimationUtils.loadAnimation(ctx, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        iv_img.setAnimation(operatingAnim);
    }

    public void stopAnimation() {
        iv_img.clearAnimation();
    }

    public void setShowDrawable(@DrawableRes int rid, String content) {
        iv_img.setImageResource(rid);
        if (rid != R.drawable.icon_loading) {
            stopAnimation();
        } else {
            startAnimation();
        }
        tv_content.setText(content);
    }

    public void setLoadingContent(String content){
        tv_content.setText(content);
    }

    public void showSucces(String content) {
        iv_img.setImageResource(R.mipmap.success);
        setContent(content);
    }

    public void showInfo(String content) {
        iv_img.setImageResource(R.mipmap.info);
        setContent(content);
    }


    public void setContent(String content) {
        stopAnimation();
        tv_content.setText(content);
        tv_content.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);
    }

    public void showError(String content) {
        iv_img.setImageResource(R.mipmap.error);
        setContent(content);
    }

    @Override
    protected int getAnimation() {
        return 0;
    }

    @Override
    protected int width() {
        return Util.getWidth() - Util.dp2Px(110);
    }

    @Override
    public void onDismiss() {
        stopAnimation();
        cancle();
        super.onDismiss();
    }

    /**
     * 取消加载
     */
    protected void cancle() {

    }
}
