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
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
