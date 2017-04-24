package base.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dengmingzhi on 2017/4/23.
 */

public class LookBigPicAdapter  extends ViewPager {

    public LookBigPicAdapter(Context context) {
        super(context);
    }

    public LookBigPicAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
