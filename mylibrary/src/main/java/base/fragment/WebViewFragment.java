package base.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import butterknife.BindView;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2016/12/29.
 */

public class WebViewFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    private WebView web_view;
    private TextView tv_load;
    private String url;
    private boolean isShowTitle;


    public static WebViewFragment getInstance(String url) {
        WebViewFragment webFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    public static WebViewFragment getInstance(String url, boolean isShowTitle) {
        WebViewFragment webFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("isShowTitle", isShowTitle);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        url = bundle.getString("url");
        isShowTitle = bundle.getBoolean("isShowTitle", true);
    }


    @SuppressLint("JavascriptInterface")
    @Override
    protected void initData() {
        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_view.addJavascriptInterface(getJavaScripInterface(), getInterfaceTag());
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    tv_load.setVisibility(View.GONE);
                    if (isShowTitle) {
                        titleBarView.setTitleContent(web_view.getTitle());
                    }
                } else {
                    tv_load.setText("正在加载 " + newProgress + "%");
                }
                super.onProgressChanged(view, newProgress);
            }

        });

        web_view.loadUrl(url);


    }

    private String getInterfaceTag() {
        return "";
    }


    private Object getJavaScripInterface() {
        return null;
    }

    @Override
    protected void initView() {
        web_view = (WebView) rootView.findViewById(R.id.web_view);
        tv_load = (TextView) rootView.findViewById(R.id.tv_load);
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_web_view;
    }

    private DefaultTitleBarView titleBarView;

    @Override
    protected void initTitleView() {
        titleBarView = ((DefaultTitleBarView) getTitleBar()).setOnTitleBarListener(this);
    }

    @Override
    public DefaultTitleBarView getTitleBarView() {
        return (isShowTitle ? new DefaultTitleBarView(getContext()) : null);
    }

    public WebView getWV() {
        return web_view;
    }

    @Override
    public void left() {
        RxBus.get().post("back","back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
