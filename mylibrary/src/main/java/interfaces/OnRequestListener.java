package interfaces;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public interface OnRequestListener<T> {
    void succes(boolean isWrite, T bean);

    void error(boolean isWrite, T bean, String msg);

    void start();

    void finish();

    void onFailed(Exception e);
}
