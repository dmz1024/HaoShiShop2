package base.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.itemanimators.SlideInOutBottomAnimator;
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.adapter.BaseAdapter;
import base.bean.ListBaseBean;
import base.bean.TipLoadingBean;
import interfaces.OnAdapterDataListener;
import interfaces.OnShowListDataListener;
import util.MyToast;
import view.DefaultShowListView;

import static base.fragment.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_HAVE_DATA;
import static base.fragment.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_DATA;
import static base.fragment.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_NETWORK;
import static base.fragment.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_SERVER_ERR;


/**
 * Created by dengmingzhi on 16/6/14.
 */
public abstract class ListNetWorkBaseFragment<D extends ListBaseBean> extends NetworkBaseFragment<D> {
    protected int page = 1;
    protected int totalPage = 1;
    protected int currentPage = 1;
    public BaseAdapter mAdapter;
    protected RequestType currentType = RequestType.LOAD_NEW;
    protected boolean isLoading;
    protected boolean isHaveData = true;
    public List totalList = new ArrayList<>();

    protected int getPage() {
        return 1;
    }

    protected int getSize() {
        return 15;
    }


    protected void initMap() {
        map.put("size", getSize() + "");
        map.put("page", page + "");
    }


    @Override
    protected Map<String, String> map() {
        initMap();
        return map;
    }

    @Override
    protected void manageFinish() {
        if (layoutManager != null && isCanRefresh()) {
            setRefresh(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
        } else {
            setRefresh(false);
        }
    }

    @Override
    protected void writeData(boolean isWrite, D bean) {
        stopRefresh();
        manageFinish();
        currentPage = page;
        ArrayList list = ((ArrayList) bean.getData());
        if (currentType == RequestType.LOAD_NEW) {
            totalList.clear();
        } else {
            //隐藏加载更多
            view.hide(1);
            isLoading = false;
        }

        if (getLoadMore() && bean.page_info != null && (bean.page_info.page_count >= bean.page_info.page_index)) {
            isHaveData = true;
            totalPage = bean.page_info.page_count;
        } else {
            isHaveData = false;
        }

        totalList.addAll(list);
        if (totalList.size() > 0) {
            getCurrentView(VIEW_HAVE_DATA);
            mAdapter.notifyDataSetChanged();
            if (currentType == RequestType.LOAD_NEW) {
                layoutManager.scrollToPosition(0);
            }
        } else {
            getCurrentView(VIEW_NO_DATA);
        }

    }

    @Override
    protected void manageError(boolean isWrite, D bean, String msg) {
        stopRefresh();
        manageMsg(msg);
        manageFinish();
        page = currentPage;
        if (currentType == RequestType.LOAD_MORE) {
            //关闭加载中操作
            view.hide(1);
            isLoading = false;
        }

        if (totalList.size() == 0) {
            getCurrentView(VIEW_SERVER_ERR);
        }
    }

    @Override
    protected void networkError() {
        manageFinish();
        if (totalList.size() == 0) {
            getCurrentView(VIEW_NO_NETWORK);
        } else {
            MyToast.showToast("请打开网络连接");
        }
    }

    @Override
    protected void serverError() {
        manageFinish();
        if (totalList.size() == 0) {
            getCurrentView(VIEW_SERVER_ERR);
        } else {
            MyToast.showToast("获取数据异常");
        }
    }


    protected void manageMsg(String msg) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        currentType = RequestType.LOAD_NEW;
        if (layoutManager != null) {
            view.hide(1);
            isLoading = false;
        }
        super.onRefresh();
    }

    @Override
    public void startRefresh() {
        if (isLoading || isRefresh) {
            return;
        }
        setRefresh(true);
        if (isFirst) {
            onRefresh();
        } else {
            super.startRefresh();
        }

    }


    protected LinearLayoutManager layoutManager;
    private OnShowListDataListener view;
    protected RecyclerView recyclerView;
    @Override
    protected View getHaveDataView() {
        view = showListView();
        recyclerView = view.getRecy();
        recyclerView.setAdapter(mAdapter = (BaseAdapter) getAdapter());
        mAdapter.setOnDataCountListener(new OnAdapterDataListener() {
            @Override
            public void totalCount(int count) {
                if (count == 0) {
                    getCurrentView(VIEW_NO_DATA);
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager = getLayoutManager());
        RecyclerView.ItemDecoration itemDecoration;
        if ((itemDecoration = getDividerItemDecoration()) != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止滑动被抢焦点
                if (isCanRefresh()) {
                    setRefresh(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                }
//
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if(!isSlidingToLast){
                       return;
                    }

                    //获取最后一个完全显示的ItemPosition,进行加载更多
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem >= (totalItemCount - getSize())) {
                        moreData();
                    }
                }


            }
        });
        return (View) view;
    }

    protected void moreData(){
        if (isRefresh || isLoading || !getLoadMore()  ||currentPage > totalPage) {
            return;

        }
        isLoading = true;
        setRefresh(false);
//                        view.loadMore();
        currentPage = page;
        page += 1;
        currentType = RequestType.LOAD_MORE;
//                    layoutManager.scrollToPosition(totalList.size() - 1);
        getData();
    }


    /**
     * 展示数据的View,需继承OnShowListDataListener
     *
     * @return
     */
    protected OnShowListDataListener showListView() {
        return new DefaultShowListView(getContext());
    }


    // 设置是否可以加载更多
    protected boolean getLoadMore() {
        return true;
    }

    /**
     * 返回适配器对象
     *
     * @return
     */
    protected abstract RecyclerView.Adapter getAdapter();


    /**
     * 加载更多时是否显示进度框
     */

    protected boolean getLoadMoreCanShowTipLoading() {
        return false;
    }

    @Override
    protected TipLoadingBean getTipLoadingBeanForListNet() {
        if (currentType == RequestType.LOAD_MORE) {
            if (getLoadMoreCanShowTipLoading()) {
                return getTipLoadingBean();
            } else {
                return null;
            }
        }
        return getTipLoadingBean();
    }

    @Override
    protected boolean isFormListNet() {
        return true;
    }

    /**
     * 返回分割线
     *
     * @return
     */
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return null;
    }

    /**
     * 返回视图管理器
     *
     * @return
     */
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    public enum RequestType {
        LOAD_MORE, LOAD_NEW
    }


}
