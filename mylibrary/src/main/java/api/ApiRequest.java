package api;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.rest.Response;

import java.util.Map;

import base.bean.BaseBean;
import base.bean.TipLoadingBean;
import interfaces.OnMyResponseListener;
import interfaces.OnRequestListener;
import util.FileUtil;
import util.JLogUtils;
import util.JsonUtil;
import util.Util;
import view.pop.TipLoading;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public abstract class ApiRequest<T extends BaseBean> {
    private static Handler mHandler = new Handler();

    protected abstract Map<String, String> getMap();

    protected abstract String getUrl();

    protected abstract Class<T> getClx();

    protected Context getContext() {
        return null;
    }

    protected boolean getShowInfo() {
        return true;
    }

    protected boolean getShowSucces() {
        return true;
    }

    protected boolean getShouldCache() {
        return false;
    }

    protected boolean getWriteCache() {
        return false;
    }

    protected Object getSign() {
        return getClx();
    }

    protected boolean getCanDis() {
        return true;
    }

    public ApiRequest get() {
        JavaBeanRequest<T> request = new JavaBeanRequest<>(getUrl(), getClx());
        creatRequest(request, null);
        return this;
    }


    public ApiRequest get(TipLoadingBean tip) {
        JavaBeanRequest<T> request = new JavaBeanRequest<>(getUrl(), getClx());
        creatRequest(request, tip);
        return this;
    }

    public ApiRequest post() {
        JavaBeanRequest<T> request = new JavaBeanRequest<>(getUrl(), RequestMethod.POST, getClx());
        creatRequest(request, null);
        return this;
    }

    public ApiRequest post(TipLoadingBean tip) {
        JavaBeanRequest<T> request = new JavaBeanRequest<>(getUrl(), RequestMethod.POST, getClx());
        creatRequest(request, tip);
        return this;
    }


    protected int getTime() {
        return 500;
    }

    private void creatRequest(final JavaBeanRequest<T> request, final TipLoadingBean tip) {

        if (!Util.isNetworkAvailable()) {
            if (onRequestListeren != null) {
                onRequestListeren.onFailed(new NetworkError());
            }
            return;
        }

        CallServer.getInstance().cancelBySign(getSign());
        request.setCancelSign(getSign());
        TipLoading tipLoading = null;
        if (tip != null) {
            tipLoading = getLoading();
            tipLoading.showAtLocation(getCanDis());
            tipLoading.setLoadingContent(tip.getLoading());
        }


        final TipLoading finalTipLoading = tipLoading;
        if (getTime() == 0) {
            request(request, tip, finalTipLoading);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    request(request, tip, finalTipLoading);
                }
            }, getTime());
        }

    }


    private void request(JavaBeanRequest<T> request, final TipLoadingBean tip, final TipLoading finalTipLoading) {


        Map<String, String> map = getMap();
        if (map != null) {
            request.add(map);
        }
        String url = Util.getUrl(map, getUrl());
        Log.d("请求网址", url);
        if (getWriteCache()) {
            String json = FileUtil.getDataFromNative(url);
            if (!TextUtils.isEmpty(json)) {
                result(true, finalTipLoading, tip, JsonUtil.json2Bean(json, getClx()));
            }
        }

//        saveCache(request, url);


        CallServer.getInstance().add(1, request, new OnMyResponseListener<T>() {
            @Override
            protected void onStart() {
                super.onStart();
                if (onRequestListeren != null) {
                    onRequestListeren.start();
                }
            }

            @Override
            protected void onFinish() {
                super.onFinish();
                if (onRequestListeren != null) {
                    onRequestListeren.finish();
                }
            }

            @Override
            protected void onSucceed(Response<T> response) {
                result(false, finalTipLoading, tip, response.get());
            }

            @Override
            protected void onFailed(Response<T> response) {
                JLogUtils.D("请求数据错误" + response.getException().getMessage());
                if (finalTipLoading != null) {
                    finalTipLoading.showError("请求错误");
                }
                if (onRequestListeren != null) {
                    onRequestListeren.onFailed(response.getException());
                }

            }
        });
    }

    private void result(boolean isWrite, TipLoading finalTipLoading, TipLoadingBean tip, T t) {
        if (t.result == 0) {
            if (finalTipLoading != null && !isWrite) {
                if (getShowSucces()) {
                    finalTipLoading.showSucces(tip.getSucces());
                } else {
                    finalTipLoading.dismiss();
                }

            }
            if (onRequestListeren != null) {
                onRequestListeren.succes(isWrite, t);
            }

        } else {
            if (finalTipLoading != null && !isWrite) {
                if (getShowInfo()) {
                    finalTipLoading.showInfo(TextUtils.isEmpty(t.msg) ? tip.getError() : t.msg);
                } else {
                    finalTipLoading.dismiss();
                }

            }

            if (onRequestListeren != null) {
                onRequestListeren.error(isWrite, t, t.msg);
            }
        }
    }

    private void saveCache(JavaBeanRequest<T> request, String url) {
        if (getShouldCache()) {
            request.setIsCache(true);
            request.setSaveUrl(url);
        }
    }


    private TipLoading getLoading() {
        return new TipLoading(getContext());
    }

    private OnRequestListener onRequestListeren;

    public ApiRequest setOnRequestListeren(OnRequestListener<T> onRequestListeren) {
        this.onRequestListeren = onRequestListeren;
        return this;
    }

}
