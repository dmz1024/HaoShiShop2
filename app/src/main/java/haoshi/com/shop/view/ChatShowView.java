package haoshi.com.shop.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import haoshi.com.shop.R;
import util.GlideUtil;
import util.WindowUtil;

/**
 * Created by dengmingzhi on 2017/3/21.
 */

public class ChatShowView extends RelativeLayout implements View.OnClickListener {
    private ImageView iv_head;
    private TextView tv_name;
    private TextView tv_content;
    private int height = 0;

    public ChatShowView(Context context) {
        this(context, null);
    }

    public ChatShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.chat_show_view, this);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_content = (TextView) findViewById(R.id.tv_content);
        setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Log.d("dianji", "ddd");
    }

    public ChatShowView setHead(String url) {
        GlideUtil.GlideErrAndOc(getContext(), url, iv_head);
        return this;
    }

    public ChatShowView setName(String name) {
        tv_name.setText(name);
        return this;
    }

    public ChatShowView setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    private ObjectAnimator translationY;

    public ChatShowView setVisi(int visi) {
        setVisibility(visi);
        return this;
    }

    public ChatShowView show() {
        if (height == 0) {
            height = getTop()-WindowUtil.getStatusBarHeight();
        }
        if (translationY == null) {
            translationY = ObjectAnimator.ofFloat(this, "translationY", 0, -height).setDuration(300);
            translationY.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    removeCallbacks(hideRun);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    postDelayed(hideRun, 2500);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
        translationY.start();
        return this;
    }

    private ObjectAnimator hideAnimator;
    private Runnable hideRun = new Runnable() {
        @Override
        public void run() {
            if (hideAnimator == null) {
                hideAnimator = ObjectAnimator.ofFloat(ChatShowView.this, "translationY", -height, 0).setDuration(300);
            }
            hideAnimator.start();
        }
    };
}
