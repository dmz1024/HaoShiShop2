package base.bean;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public class BaseBean<T> {
    public int result;
    public String msg;
    public T data;

    public T getData(){
        return data;
    }
}
