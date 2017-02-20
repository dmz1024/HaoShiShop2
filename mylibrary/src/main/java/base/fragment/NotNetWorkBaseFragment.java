package base.fragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import base.fragment.BaseFragment;
import butterknife.ButterKnife;
import util.Util;
import view.GmRefreshLayout;

/**
 * Created by dengmingzhi on 2016/11/22.
 */

public abstract class NotNetWorkBaseFragment extends BaseFragment {

//    @Override
//    protected void rootAddContentView(FrameLayout root) {
//        View view = View.inflate(getContext(), getRId(), null);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        params.setMargins(0, getTop(), 0, 0);
//        view.setLayoutParams(params);
//        root.addView(view);
//        ButterKnife.bind(this, root);
//        initData();
//    }
//
//    @Override
//    protected View getTitleBarView() {
//        return null;
//    }
//
//    protected abstract int getRId();
//
//    protected abstract void initData();

    protected boolean isFirst = true;
    protected boolean isVisible;
    /**
     * 标志位，标志视图已经初始化完成
     */
    boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {

        if (isOnlyInitOne() && !isFirst) {
            return;
        }

        if (!isVisible) {
            return;
        }


        if (!isPrepared) {
            return;
        }

        onRefresh();

    }


    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    protected void onRefresh() {
        isFirst = false;
        initData();
    }


    @Override
    protected void rootAddContentView(FrameLayout rootView) {
        View view = View.inflate(getContext(), getRId(), null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, getTop(), 0, 0);
        view.setLayoutParams(params);
        rootView.addView(view);
        ButterKnife.bind(this, rootView);
        isPrepared = true;
        initView();
        if (isCanFirstInitData()) {
            onRefresh();
        }
    }

    protected void initView() {

    }


    /**
     * 设置在setUserVisibleHint可见的是否重复刷新数据
     *
     * @return
     */
    protected boolean isOnlyInitOne() {
        return true;
    }


    /**
     * 是否在加载视图之后立马加载数据
     *
     * @return
     */
    protected boolean isCanFirstInitData() {
        return true;
    }


    /**
     * 初始化数据
     */
    protected abstract void initData();


    protected abstract int getRId();

}
