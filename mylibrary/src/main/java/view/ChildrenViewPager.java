package view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class ChildrenViewPager extends ViewPager {

    public ChildrenViewPager(Context context) {
        super(context);
    }

    public ChildrenViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d("父类",getParent().getParent().getParent().getParent().getParent().getClass().getName());
//        getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }
}
