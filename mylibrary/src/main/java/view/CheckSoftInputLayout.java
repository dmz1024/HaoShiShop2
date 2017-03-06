package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by dengmingzhi on 2017/3/3.
 */

public class CheckSoftInputLayout extends FrameLayout {
    private OnResizeListener mOnResizeListener;
    public CheckSoftInputLayout(Context context) {
        super(context);
    }
    public CheckSoftInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CheckSoftInputLayout(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public CheckSoftInputLayout(Context context, AttributeSet attrs, int
            defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int old) {
        super.onSizeChanged(w, h, oldw, old);
        if (mOnResizeListener != null) {
            mOnResizeListener.onResize(w, h, oldw, old);
        }
    }
    public void setOnResizeListener(OnResizeListener listener) {
        this.mOnResizeListener = listener;
    }
    public interface OnResizeListener {
        void onResize(int w, int h, int oldw, int old);
    }
}

////如果第一次初始化
//if (oldh == 0) {
//        return;
//        }
////如果用户横竖屏转换
//        if (w != oldw) {
//        return;
//        }
//        if (h < oldh) {
////输入法弹出
//        } else if (h > oldh) {
////输入法关闭
//        setCommentViewEnabled(false, false);
//        }
//        int distance = h - old;