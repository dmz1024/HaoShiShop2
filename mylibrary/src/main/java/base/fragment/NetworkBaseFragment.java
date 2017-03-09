package base.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;

import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import api.CallServer;
import base.bean.BaseBean;
import base.bean.TipLoadingBean;
import interfaces.OnSingleRequestListener;
import util.MyToast;

/**
 * Created by dengmingzhi on 16/6/14.
 */
public abstract class NetworkBaseFragment<D extends BaseBean> extends RefreshBaseFragment {
    protected Map<String, String> map = new HashMap<>();
    protected View noDataView;
    protected View havaDataView;
    protected View noNetworkView;
    protected View serverErrView;
    protected View isLoadingView;
    protected ShowCurrentViewENUM currentView;
    public boolean result;

    @Override
    protected void initData() {
        getData();
    }


    @Override
    protected View getShowView() {
        switch (currentView = getDefaultView()) {
            case VIEW_IS_LOADING:
                return isLoadingView = getIsLoadingView();
            case VIEW_HAVE_DATA:
                return havaDataView = getHaveDataView();
            case VIEW_NO_DATA:
                return noDataView = getNoDataView();
            case VIEW_SERVER_ERR:
                return serverErrView = getServerErrView();
            case VIEW_NO_NETWORK:
                return noNetworkView = getNoNetworkView();
        }

        return isLoadingView = getIsLoadingView();
    }


    /**
     * 第一次进入页面的时候默认加载的页面
     *
     * @return
     */
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_IS_LOADING;
    }

    /**
     * 获取网络数据
     */
    protected void getData() {
        ApiRequest apiRequest = new ApiRequest<D>() {
            @Override
            protected Map<String, String> getMap() {
                return map();
            }

            @Override
            protected Context getContext() {
                return NetworkBaseFragment.this.getContext();
            }

            @Override
            protected String getUrl() {
                return url();
            }

            @Override
            protected Class<D> getClx() {
                return getTClass();
            }

            @Override
            protected boolean getShowSucces() {
                return showSucces();
            }

            @Override
            protected boolean getWriteCache() {
                return writeCache();
            }

            @Override
            protected int getTime() {
                return time();
            }

            @Override
            protected boolean getShouldCache() {
                return shouldCache();
            }

            @Override
            protected boolean getCanDis() {
                return getDis();
            }
        }.setOnRequestListeren(new OnSingleRequestListener<D>() {
            @Override
            public void succes(boolean isWrite, D bean) {
                getCurrentView(ShowCurrentViewENUM.VIEW_HAVE_DATA);
                writeData(isWrite, bean);
                manageFinish();
            }

            @Override
            public void error(boolean isWrite, D d, String msg) {
                stopRefresh();
                manageError(isWrite, d, msg);
                manageFinish();
            }

            @Override
            public void onFailed(Exception e) {
                stopRefresh();
                failed(e);
                manageFinish();
            }
        });


        TipLoadingBean loadingBean = isFormListNet() ? getTipLoadingBeanForListNet() : getTipLoadingBean();
        if (loadingBean != null) {
            if (getMethod()) {
                apiRequest.post(loadingBean);
            } else {
                apiRequest.get(loadingBean);
            }
        } else {
            if (getMethod()) {
                apiRequest.post();
            } else {
                apiRequest.get();
            }
        }

    }

    protected boolean getDis() {
        return true;
    }

    protected TipLoadingBean getTipLoadingBeanForListNet() {
        return null;
    }


    protected boolean isFormListNet() {
        return false;
    }

    protected void manageFinish() {

    }


    /**
     * 延时加载数据时间
     *
     * @return
     */
    protected int time() {
        return 100;
    }


    /**
     * 在成功请求数据之后并且在显示加载框的情况下判断是否显示加载成功字样
     *
     * @return
     */
    protected boolean showSucces() {
        return false;
    }

    /**
     * 非正常错误导致的异常
     *
     * @param e
     */
    protected void failed(Exception e) {
        if (e instanceof NetworkError) {
            networkError();
        } else if (e instanceof ServerError || e instanceof TimeoutError) {
            serverError();
        } else if (e instanceof ParseError) {
            MyToast.showToast("数据格式异常");
        }
    }

    protected void networkError() {
        getCurrentView(ShowCurrentViewENUM.VIEW_NO_NETWORK);
    }

    protected void serverError() {
        getCurrentView(ShowCurrentViewENUM.VIEW_SERVER_ERR);
    }


    /**
     * result=1的情况下的数据处理
     *
     * @param isWrite
     * @param d
     * @param msg
     */
    protected abstract void manageError(boolean isWrite, D d, String msg);

    /**
     * result=0的情况下的数据处理
     *
     * @param isWrite
     * @param bean
     */
    protected abstract void writeData(boolean isWrite, D bean);

    /**
     * 是否读取缓存
     *
     * @return
     */
    protected boolean writeCache() {
        return false;
    }

    /**
     * 是否进行数据缓存
     *
     * @return
     */
    protected boolean shouldCache() {
        return false;
    }


    /**
     * 请求数据时是否显示加载框
     *
     * @return
     */
    protected TipLoadingBean getTipLoadingBean() {
        return null;
    }

    /**
     * 请求方式 true:POST  false:GET
     *
     * @return
     */
    protected boolean getMethod() {
        return false;
    }


    /**
     * 返回http请求地址
     *
     * @return
     */
    protected abstract String url();

    /**
     * 参数集合
     *
     * @return
     */
    protected Map<String, String> map() {
        return map;
    }


    /**
     * 返回数据转换之后的类型
     *
     * @return
     */
    protected abstract Class<D> getTClass();


    public enum ShowCurrentViewENUM {
        //有数据、没有数据,没有网络,服务器错误,正在加载数据
        VIEW_HAVE_DATA, VIEW_NO_DATA, VIEW_NO_NETWORK, VIEW_SERVER_ERR, VIEW_IS_LOADING;
    }


    /**
     * 根据请求服务器返回的数据来判断当前显示的视图
     *
     * @param type
     */
    protected void getCurrentView(ShowCurrentViewENUM type) {
        View showView = null;

        switch (type) {
            case VIEW_HAVE_DATA:
                if (havaDataView == null) {
                    havaDataView = getHaveDataView();
                }

                showView = havaDataView;
                break;
            case VIEW_NO_DATA:
                if (noDataView == null) {
                    noDataView = getNoDataView();
                }
                showView = noDataView;
                break;
            case VIEW_NO_NETWORK:
                if (noNetworkView == null) {
                    noNetworkView = getNoNetworkView();
                }
                showView = noNetworkView;
                break;
            case VIEW_SERVER_ERR:
                if (serverErrView == null) {
                    serverErrView = getServerErrView();
                }
                showView = serverErrView;
                break;
            case VIEW_IS_LOADING:
                if (isLoadingView == null) {
                    isLoadingView = getIsLoadingView();
                }
                showView = isLoadingView;
                break;
        }

        if (type != currentView) {
            currentView = type;
            root.removeAllViews();
            root.addView(showView);
        }

    }


    /**
     * 数据异常
     *
     * @return
     */
    protected View getServerErrView() {
        TextView view = (TextView) View.inflate(getContext(), R.layout.net_work_show_view, null);
        view.setText("服务器出现错误，请重试");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentView(ShowCurrentViewENUM.VIEW_IS_LOADING);
                loadingFromServerErr();
            }
        });
        return view;
    }

    protected void loadingFromServerErr() {
        isFirst = true;
        startRefresh();
    }

    /**
     * 网络异常
     *
     * @return
     */
    protected View getNoNetworkView() {
        TextView view = (TextView) View.inflate(getContext(), R.layout.net_work_show_view, null);
        view.setText("您的网络出现问题了，请检查重试");
        return view;
    }

    /**
     * 空数据
     *
     * @return
     */
    protected View getNoDataView() {
        TextView view = (TextView) View.inflate(getContext(), R.layout.net_work_show_view, null);
        view.setText("什么都木有");
        return view;
    }

    /**
     * 有数据
     *
     * @return
     */
    protected abstract View getHaveDataView();

    /**
     * 正在加载
     *
     * @return
     */
    protected View getIsLoadingView() {
        TextView view = (TextView) View.inflate(getContext(), R.layout.net_work_show_view, null);
        view.setText("正在加载...");
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (root != null) {
            root.removeAllViews();
            isFirst = true;
        }
        CallServer.getInstance().cancelBySign(getTClass());
    }


}
