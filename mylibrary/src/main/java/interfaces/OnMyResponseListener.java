package interfaces;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public abstract class OnMyResponseListener<T> implements OnResponseListener<T> {
    @Override
    public void onStart(int what) {
        onStart();
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        onSucceed(response);
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        onFailed(response);
    }

    @Override
    public void onFinish(int what) {
        onFinish();
    }

    protected void onStart() {
    }

    protected void onFinish() {
    }

    protected abstract void onSucceed(Response<T> response);

    protected abstract void onFailed(Response<T> response);
}
