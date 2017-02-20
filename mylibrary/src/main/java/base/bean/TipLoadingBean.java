package base.bean;

import android.text.TextUtils;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public class TipLoadingBean {
    private String loading;
    private String succes;
    private String error;

    public TipLoadingBean(String loading, String succes, String error) {
        this.loading = loading;
        this.succes = succes;
        this.error = error;
    }

    public TipLoadingBean() {
    }

    public String getLoading() {
        return TextUtils.isEmpty(loading) ? "正在加载..." : loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getSucces() {
        return TextUtils.isEmpty(succes) ? "加载成功" : succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }

    public String getError() {
        return TextUtils.isEmpty(error) ? "加载失败" : error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
