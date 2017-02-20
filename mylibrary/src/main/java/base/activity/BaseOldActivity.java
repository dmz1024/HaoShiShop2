package base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mall.naiqiao.mylibrary.R;

/**
 * Created by dengmingzhi on 2016/11/22.
 */
public class BaseOldActivity extends AppCompatActivity {
    private GestureDetector mGestureDetector;

    private void initGesture() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                // // System.out.println("水平方向移动距离过大");
                // return true;
                // }
//                if (Math.abs(velocityY) < 50) {
//                     System.out.println("手指移动的太慢了");
//                    return true;
//                }

                if (e2.getRawX() - e1.getRawX() > 150) {
                    left();
                    return true;
                }

                if (e1.getRawX() - e2.getRawX() > 150) {
                    right();
                    return true;
                }

                // 手势向下 down
                if ((e2.getRawY() - e1.getRawY()) > 150) {
                    top();
                    return true;
                }
                // 手势向上 up
                if ((e1.getRawY() - e2.getRawY()) > 150) {
                    bottom();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }

    protected void bottom() {
//        overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);
//        finish();
    }

    protected void top() {
//        overridePendingTransition(R.anim.slide_up_out,R.anim.slide_up_in);
//        finish();
    }

    protected void right() {
//        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
//        finish();
    }

    protected void left() {
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needGesture()) {
            initGesture();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否需要进行手势返回
     *
     * @return
     */
    protected boolean needGesture() {
        return true;
    }


}
