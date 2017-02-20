package api;

import android.text.TextUtils;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import util.FileUtil;
import util.JLogUtils;
import util.JsonUtil;

/**
 * Created by dengmingzhi on 2016/11/14.
 */

public class JavaBeanRequest<T> extends RestRequest<T> {
    // 要解析的JavaBean的class。
    private Class<T> clazz;
    private boolean isCache = false;
    private String saveUrl;

    public JavaBeanRequest(String url, Class<T> clazz) {
        this(url, RequestMethod.GET, clazz);
    }

    public void setIsCache(boolean isCache) {
        this.isCache = isCache;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    public JavaBeanRequest(String url, RequestMethod requestMethod, Class<T> clazz) {
        super(url, requestMethod);
        this.clazz = clazz;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        final String response = StringRequest.parseResponseString(responseHeaders, responseBody);
        if (isCache && !TextUtils.isEmpty(saveUrl)) {
            new Thread(){
                @Override
                public void run() {
                    FileUtil.setData2Native(saveUrl, response);
                }
            }.start();
        }
        JLogUtils.Json("返回数据", response);
        // 这里如果数据格式错误，或者解析失败，会在失败的回调方法中返回 ParseError 异常。
        return JsonUtil.json2Bean(response, clazz);
    }
}
