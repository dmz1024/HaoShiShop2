package haoshi.com.shop.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import haoshi.com.shop.R;
import util.DrawableUtil;

/**
 * Created by dengmingzhi on 2016/11/22.
 */

public class FriendAndFlockTitleBarView extends FrameLayout implements View.OnClickListener {
    private ImageView title_bar_iv_left;
    private TextView title_bar_tv_right;
    private TextView title_bar_tv_flock;
    private TextView title_bar_tv_friend;
    private View title_bar_view;
    private FrameLayout title_bar_fg_left;

    public FriendAndFlockTitleBarView(Context context) {
        this(context, null);
    }

    public FriendAndFlockTitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.title_bar_friend_flock, this);
        title_bar_iv_left = (ImageView) findViewById(R.id.title_bar_iv_left);
        title_bar_tv_right = (TextView) findViewById(R.id.title_bar_tv_right);
        title_bar_tv_flock = (TextView) findViewById(R.id.title_bar_tv_flock);
        title_bar_tv_friend = (TextView) findViewById(R.id.title_bar_tv_friend);
        title_bar_view = findViewById(R.id.title_bar_view);
        title_bar_fg_left = (FrameLayout) findViewById(R.id.title_bar_fg_left);
    }


    private OnFriendAndFlockTitleBarListener onTitleBarListener;

    public FriendAndFlockTitleBarView setOnTitleBarListener(OnFriendAndFlockTitleBarListener onTitleBarListener) {
        if (onTitleBarListener != null) {
            title_bar_fg_left.setOnClickListener(this);
            title_bar_tv_right.setOnClickListener(this);
            title_bar_tv_flock.setOnClickListener(this);
            title_bar_tv_friend.setOnClickListener(this);
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
        } else if (title_bar_tv_flock == v) {
            onTitleBarListener.flock();
            changeColor(1);
        } else if (title_bar_tv_friend == v) {
            changeColor(2);
            onTitleBarListener.friend();
        }
    }

    private void changeColor(int index) {
        title_bar_tv_flock.setTextColor(Color.parseColor(index == 1 ? "#ffffff" : "#393934"));
        title_bar_tv_friend.setTextColor(Color.parseColor(index == 2 ? "#ffffff" : "#393934"));
        title_bar_tv_flock.setBackgroundResource(index == 1 ? R.drawable.hs_shape_666_left_radius_5 : R.drawable.hs_shape_fff_left_radius_5);
        title_bar_tv_friend.setBackgroundResource(index == 2 ? R.drawable.hs_shape_666_right_radius_5 : R.drawable.hs_shape_fff_right_radius_5);
    }


    public FriendAndFlockTitleBarView setLeftImage(@DrawableRes int rid) {
        title_bar_iv_left.setImageResource(rid);
        return this;
    }

    public FriendAndFlockTitleBarView setRightImage(@DrawableRes int rid) {
        title_bar_tv_right.setCompoundDrawables(null, null, DrawableUtil.setBounds(getResources().getDrawable(rid)), null);
        return this;
    }


    public FriendAndFlockTitleBarView setRightContent(String content) {
        title_bar_tv_right.setText(content);
        return this;
    }


    public FriendAndFlockTitleBarView setRightColor(String color) {
        title_bar_tv_right.setTextColor(Color.parseColor(color));
        return this;
    }

    public FriendAndFlockTitleBarView setRightSize(int size) {
        title_bar_tv_right.setTextSize(size);
        return this;
    }

    public FriendAndFlockTitleBarView showVisiView(int visi) {
        title_bar_view.setVisibility(visi);
        return this;
    }

    public FriendAndFlockTitleBarView showVisiLeft(int visi) {
        title_bar_fg_left.setVisibility(visi);
        return this;
    }


    public interface OnFriendAndFlockTitleBarListener {
        void left();

        void flock();

        void friend();

        void right();
    }

}
