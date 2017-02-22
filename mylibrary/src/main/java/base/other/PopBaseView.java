package base.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.github.florent37.viewanimator.ViewAnimator;
import com.mall.naiqiao.mylibrary.R;

import util.Util;


/**
 * Created by dengmingzhi on 2016/10/22.
 */

public abstract class PopBaseView implements PopupWindow.OnDismissListener {
    protected Context ctx;
    protected PopupWindow popupWindow;
    private View view;
    protected boolean isAlpha() {
        return true;
    }


    public PopBaseView(Context ctx) {
        this.ctx = ctx;
//        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, getAlpha()).duration(getShowTime()).start();

        if (isAlpha()) {
            ((Activity) ctx).findViewById(android.R.id.content).setAlpha(getAlpha());
        }
    }


    protected long getShowTime() {
        return 400;
    }

    protected float getAlpha() {
        return 0.5f;
    }

    protected long getDismissTime() {
        return 100;
    }

    private void creatPop(boolean cancel) {
        popupWindow = new PopupWindow((view = getView()), Util.getWidth() - width(), height(), true);
        if (!cancel) {
            popupWindow.setBackgroundDrawable(new ColorDrawable());
        }
        popupWindow.setOnDismissListener(this);
        int animation = getAnimation();
        if (animation != 0) {
            popupWindow.setAnimationStyle(animation);
        }

    }

    protected int height() {
        return FrameLayout.LayoutParams.WRAP_CONTENT;
    }

    public void showAtLocation() {
        showAtLocation(true);
    }

    public void showAtLocation(boolean cancel) {
        creatPop(cancel);
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), getGravity(), x(), y());

    }

    public void showAsDropDown(View v) {
        showAsDropDown(v, true);

    }

    public void showAsDropDown(View v, boolean cancel) {
        creatPop(cancel);
        popupWindow.showAsDropDown(v, x(), y());
    }

    protected int x() {
        return 0;
    }

    protected int y() {
        return 0;
    }


    /**
     * View显示位置
     *
     * @return
     */
    protected int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * pop显示动画
     *
     * @return
     */
    protected int getAnimation() {
        return R.style.popwin_anim_up_and_down;
    }


    protected int width() {
        return 0;
    }

    protected abstract View getView();

    @Override
    public void onDismiss() {
//        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(getAlpha(), 1f).duration(getShowTime() + getDismissTime()).start();
//        (Activity) ctx).findViewById(android.R.id.content
        ((Activity) ctx).findViewById(android.R.id.content).setAlpha(1f);
        popupWindow = null;
    }

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public View view() {
        return view;
    }

}
