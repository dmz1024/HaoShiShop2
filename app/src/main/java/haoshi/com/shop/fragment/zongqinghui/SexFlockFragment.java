package haoshi.com.shop.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.FlockRecommendAdapter;
import haoshi.com.shop.bean.zongqinghui.FlockBean;
import haoshi.com.shop.bean.zongqinghui.SexFlockBean;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class SexFlockFragment extends ListNetWorkBaseFragment<SexFlockBean> implements OnTitleBarListener {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FlockRecommendAdapter(getContext(), (ArrayList<FlockBean>) totalList, 1);
    }

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "list");
        return super.map();
    }

    @Override
    protected Class<SexFlockBean> getTClass() {
        return SexFlockBean.class;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("性别相关").setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
