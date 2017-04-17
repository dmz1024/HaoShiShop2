package view.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import base.other.PopBaseView;
import util.MyToast;
import util.Util;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public class TipLoading extends PopBaseView {
//    private ImageView iv_info;
//    private TextView tv_content;
//    private ProgressBar pb_loading;

    public TipLoading(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.tip_loading, null);
//        iv_info = (ImageView) view.findViewById(R.id.iv_info);
//        tv_content = (TextView) view.findViewById(R.id.tv_content);
//        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        return view;
    }

    @Override
    protected float getAlpha() {
        return 1f;
    }


    public void stopAnimation() {
//        iv_info.clearAnimation();
    }

    public void setShowDrawable(@DrawableRes int rid, String content) {
//        tv_content.setText(content);
    }

    public void setLoadingContent(String content) {
//        tv_content.setText(content);
    }

    public void showSucces(String content) {
        MyToast.showToast(content);
        dismiss();
    }

    public void showInfo(String content) {
//        iv_info.setVisibility(View.VISIBLE);
//        pb_loading.setVisibility(View.GONE);
//        setContent(content);
        MyToast.showToast(content);
        dismiss();
    }


    public void setContent(String content) {
//        tv_content.setText(content);
//        tv_content.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismiss();
//            }
//        }, 2000);
    }

    public void showError(String content) {
        showInfo(content);
    }

    @Override
    protected int getAnimation() {
        return 0;
    }

}
