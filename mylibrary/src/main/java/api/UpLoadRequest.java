package api;

import android.text.TextUtils;

import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import java.util.List;
import java.util.Map;

import base.bean.BaseBean;
import interfaces.OnMyResponseListener;
import interfaces.OnRequestListener;
import util.JLogUtils;
import view.pop.TipLoading;

/**
 * Created by dengmingzhi on 2017/2/14.
 */

public abstract class UpLoadRequest<T extends BaseBean> {


    public void request(final OnRequestListener<T> listener, final TipLoading finalTipLoading) {
        Map<String, String> map = getMap();
        StringBuilder sb = new StringBuilder(getUrl());
        if (map != null) {
            for (String key : map.keySet()) {
                if (sb.toString().indexOf("?") == -1) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(map.get(key));
            }
            JLogUtils.D(sb.toString());
        }
        JavaBeanRequest<T> request = new JavaBeanRequest<>(sb.toString(), RequestMethod.POST, getClx());

        request.add(getKey(), getFiles()).setMultipartFormEnable(true);
        CallServer.getInstance().add(1, request, new OnMyResponseListener<T>() {
            @Override
            protected void onStart() {
                super.onStart();
                if (listener != null) {
                    listener.start();
                }
                if (finalTipLoading != null) {
                    finalTipLoading.showAtLocation(true);
                    finalTipLoading.setLoadingContent("正在上传");
                }
            }

            @Override
            protected void onFinish() {
                super.onFinish();
                if (listener != null) {
                    listener.finish();
                }
            }

            @Override
            protected void onSucceed(Response<T> response) {
                T t = response.get();
                if (t.result == 0) {
                    if (finalTipLoading != null) {
                        if (getShowSucces()) {
                            finalTipLoading.showSucces("上传成功");
                        } else {
                            finalTipLoading.dismiss();
                        }

                    }

                    if (listener != null) {
                        listener.succes(false, t);
                    }
                } else {
                    if (finalTipLoading != null) {
                        finalTipLoading.showError(t.msg);
                    }
                    if (listener != null) {
                        listener.error(false, t, t.msg);
                    }
                }
            }

            @Override
            protected void onFailed(Response<T> response) {
                JLogUtils.D("请求数据错误" + response.getException().getMessage());
                if (finalTipLoading != null) {
                    finalTipLoading.showError("请求错误");
                }
                if (listener != null) {
                    listener.onFailed(response.getException());
                }

            }
        });
    }

    protected boolean getShowSucces() {
        return true;
    }


    protected abstract List<Binary> getFiles();

    protected abstract Class<T> getClx();

    protected abstract String getUrl();

    protected String getKey() {
        return "file[]";
    }

    protected Map<String, String> getMap() {
        return null;
    }
}
