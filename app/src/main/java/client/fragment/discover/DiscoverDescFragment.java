package client.fragment.discover;

import android.view.View;

import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.CeshiUrl;
import client.R;
import client.bean.DiscoverDescBean;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class DiscoverDescFragment extends SingleNetWorkBaseFragment<DiscoverDescBean> implements OnTitleBarListener {
    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "single");
        return super.map();
    }

    @Override
    protected Class<DiscoverDescBean> getTClass() {
        return DiscoverDescBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_discover_desc, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected String getBackColor() {
        return "#f5f5f5";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("详情")
                .setRightImage(R.mipmap.fenxiang)
                .setOnTitleBarListener(this);
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
