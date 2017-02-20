package view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import interfaces.OnShowListDataListener;

/**
 * Created by dengmingzhi on 2016/11/21.
 */

public class DefaultShowListView extends RelativeLayout implements OnShowListDataListener {
    private RecyclerView rv_content;
    private TextView tv_content;

    private HideRunnable hideRunnable = new HideRunnable();

    public DefaultShowListView(Context context) {
        this(context, null);
    }

    public DefaultShowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.list_show_view, this);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }


    @Override
    public void loadMore() {
        tv_content.setVisibility(VISIBLE);
        removeCallbacks(hideRunnable);
        tv_content.setText("正在加载更多...");

    }


    @Override
    public void showPage(int currentPage, int totalPage) {
        tv_content.setVisibility(VISIBLE);
        tv_content.setText(currentPage + "/" + totalPage);
        hide(500);
    }

    @Override
    public void lastPage() {
        tv_content.setVisibility(VISIBLE);
        tv_content.setText("已是最后一页");
        hide(500);
    }


    @Override
    public RecyclerView getRecy() {
        return rv_content;
    }

    @Override
    public void hide(int time) {
        removeCallbacks(hideRunnable);
        postDelayed(hideRunnable, time);
    }

    class HideRunnable implements Runnable {

        @Override
        public void run() {
            tv_content.setVisibility(GONE);
        }
    }


}
