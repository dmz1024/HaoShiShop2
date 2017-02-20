package base.other;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mall.naiqiao.mylibrary.R;

/**
 * Created by dengmingzhi on 2016/11/24.
 */

public abstract class TitleBarBaseView extends LinearLayout implements View.OnClickListener{
    protected FrameLayout title_bar_fg_bar;
    private int barHeight;
    public TitleBarBaseView(Context context) {
        this(context,null);
    }

    public TitleBarBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.title_bar_base_view,this);
        title_bar_fg_bar= (FrameLayout)findViewById(R.id.title_bar_fg_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setShowBar();
        }
        addView(getTitleView());
    }

    protected abstract View getTitleView();


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        barHeight = result;
        return result;
    }


    public int getBarHeight() {
        return barHeight;
    }

    public void setBarColor(String color) {
        title_bar_fg_bar.setBackgroundColor(Color.parseColor(color));
    }


    private void setShowBar() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_bar_fg_bar.getLayoutParams();
        params.height = getStatusBarHeight();
        title_bar_fg_bar.setLayoutParams(params);
    }

}
