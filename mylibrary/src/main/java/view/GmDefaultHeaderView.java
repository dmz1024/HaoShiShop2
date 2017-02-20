package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import interfaces.OnRefreshHeader;
import interfaces.State;

/**
 * Created by dengmingzhi on 2016/11/16.
 */
public class GmDefaultHeaderView extends FrameLayout implements OnRefreshHeader {

    private Animation rotate_infinite;
    private TextView textView;
    private ImageView loadingIcon;

    public GmDefaultHeaderView(Context context) {
        this(context, null);
    }

    public GmDefaultHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rotate_infinite = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite);
        inflate(context, R.layout.mg_default_refresh_header, this);
        textView = (TextView) findViewById(R.id.text);
        loadingIcon = (ImageView) findViewById(R.id.loadingIcon);
    }

    @Override
    public void reset() {
        textView.setText("下拉刷新");
        loadingIcon.setVisibility(INVISIBLE);
        loadingIcon.clearAnimation();
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
        loadingIcon.setImageResource(R.mipmap.gpt);
        loadingIcon.setVisibility(VISIBLE);
        textView.setText("正在刷新...");
        loadingIcon.startAnimation(rotate_infinite);
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        // 往上拉
        if (currentPos < refreshPos && lastPos >= refreshPos) {
            if (isTouch && state == State.PULL) {
                textView.setText("下拉刷新");
            }
            // 往下拉
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state == State.PULL) {
                textView.setText("释放立即刷新");
            }
        }
    }

    @Override
    public void complete() {
        loadingIcon.setImageResource(R.mipmap.enm);
        loadingIcon.setVisibility(VISIBLE);
        loadingIcon.clearAnimation();
        textView.setText("刷新成功");
    }
}
