package view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import interfaces.OnTitleBarListener;
import util.DrawableUtil;

/**
 * Created by dengmingzhi on 2016/11/22.
 */

public class DefaultTitleBarView extends FrameLayout implements View.OnClickListener {
    private ImageView title_bar_iv_left;
    private TextView title_bar_tv_right;
    private TextView title_bar_tv_title;
    private View title_bar_view;
    private FrameLayout title_bar_fg_left;

    public DefaultTitleBarView(Context context) {
        this(context, null);
    }

    public DefaultTitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.default_title_bar_view, this);
        title_bar_iv_left = (ImageView) findViewById(R.id.title_bar_iv_left);
        title_bar_tv_right = (TextView) findViewById(R.id.title_bar_tv_right);
        title_bar_tv_title = (TextView) findViewById(R.id.title_bar_tv_title);
        title_bar_view = findViewById(R.id.title_bar_view);
        title_bar_fg_left = (FrameLayout) findViewById(R.id.title_bar_fg_left);
    }


    private OnTitleBarListener onTitleBarListener;

    public DefaultTitleBarView setOnTitleBarListener(OnTitleBarListener onTitleBarListener) {
        if (onTitleBarListener != null) {
            title_bar_fg_left.setOnClickListener(this);
            title_bar_tv_right.setOnClickListener(this);
            title_bar_tv_title.setOnClickListener(this);
            this.onTitleBarListener = onTitleBarListener;
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        if (title_bar_fg_left == v) {
            onTitleBarListener.left();
        } else if (title_bar_tv_right == v) {
            onTitleBarListener.right();
        } else if (title_bar_fg_left == v) {
            onTitleBarListener.center();
        }
    }


    public DefaultTitleBarView setLeftImage(@DrawableRes int rid) {
        title_bar_iv_left.setImageResource(rid);
        return this;
    }

    public DefaultTitleBarView setRightImage(@DrawableRes int rid){
        title_bar_tv_right.setCompoundDrawables(null,null, DrawableUtil.setBounds(getResources().getDrawable(rid)),null);
        return this;
    }


    public DefaultTitleBarView setRightContent(String content) {
        title_bar_tv_right.setText(content);
        return this;
    }

    public DefaultTitleBarView setTitleContent(String content) {
        title_bar_tv_title.setText(content);
        return this;
    }

    public DefaultTitleBarView setTitleColor(String color) {
        title_bar_tv_title.setTextColor(Color.parseColor(color));
        return this;
    }

    public DefaultTitleBarView setRightColor(String color) {
        title_bar_tv_right.setTextColor(Color.parseColor(color));
        return this;
    }

    public DefaultTitleBarView setTitleSize(int size) {
        title_bar_tv_title.setTextSize(size);
        return this;
    }

    public DefaultTitleBarView setRightSize(int size) {
        title_bar_tv_right.setTextSize(size);
        return this;
    }

    public DefaultTitleBarView showVisiView(int visi) {
        title_bar_view.setVisibility(visi);
        return this;
    }

    public DefaultTitleBarView showVisiLeft(int visi) {
        title_bar_fg_left.setVisibility(visi);
        return this;
    }


}
