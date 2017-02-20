package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import interfaces.ScrollChangeViewListener;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class ScrollChangeScrollView extends ScrollView {
    private ScrollChangeViewListener scrollViewListener = null;

    public ScrollChangeScrollView(Context context) {
        super(context);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setScrollViewListener(ScrollChangeViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
