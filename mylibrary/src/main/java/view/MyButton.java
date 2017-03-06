package view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import butterknife.OnLongClick;

/**
 * Created by dengmingzhi on 2017/2/23.
 */

public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isCancel;
    private long startTime = 0;//录音开始时间
    private long endTime = 0;//录音结束时间
    private int validTime = 1000;//单位毫秒
    private int maxTime = 1000 * 60;//录音最大时间

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (onMyTouchDownListener==null) {
            return super.onTouchEvent(event);
        }

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isCancel = false;
                startTime = System.currentTimeMillis();
                timer.start();
                onMyTouchDownListener.onDown();
                break;
            case MotionEvent.ACTION_MOVE:
                // 根据x, y的坐标，判断是否想要取消
                if(onTounchListener==null){
                    break;
                }
                if (wantToCancel(x, y)) {
                    isCancel = true;
                    onTounchListener.onWanCancel();
                } else {
                    isCancel = false;
                    onTounchListener.onRecording();
                }
                break;
            case MotionEvent.ACTION_UP:
                timer.cancel();
                endTime = System.currentTimeMillis();
//                if (endTime - startTime < validTime) {
//                    onTounchListener.onTimeNoValid();
//                } else {
//                    onTounchListener.onUp(isCancel);
//                }
                if(onTounchListener==null){
                    break;
                }
                onTounchListener.onUp(isCancel);


                break;
        }
        return super.onTouchEvent(event);
    }

    private OnMyTounchListener onTounchListener;

    public void setOnTounchListener(OnMyTounchListener onTounchListener) {
        this.onTounchListener = onTounchListener;
    }


    public interface OnMyTounchListener {


        void onWanCancel();

        void onRecording();

        void onUp(boolean isCancel);

        void onTimeNoValid();//时间过短

    }

    public interface OnMyTouchDownListener {
        void onDown();
    }

    private OnMyTouchDownListener onMyTouchDownListener;

    public void setOnMyTouchDownListener(OnMyTouchDownListener onMyTouchDownListener) {
        this.onMyTouchDownListener = onMyTouchDownListener;
    }

    private CountDownTimer timer = new CountDownTimer(maxTime, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            onTounchListener.onUp(false);
        }
    };


    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -250 || y > getHeight() + 250) {
            return true;
        }
        return false;
    }


}
