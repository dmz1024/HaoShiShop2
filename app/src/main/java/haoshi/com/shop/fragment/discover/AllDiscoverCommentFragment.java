package haoshi.com.shop.fragment.discover;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.AllDiscoverCommentAdapter;
import haoshi.com.shop.bean.discover.AllDiscoverCommentBean;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class AllDiscoverCommentFragment extends ListNetWorkBaseFragment<AllDiscoverCommentBean> implements OnTitleBarListener {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AllDiscoverCommentAdapter(getContext(), (ArrayList<AllDiscoverCommentBean.Data>) totalList);
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
    protected Class<AllDiscoverCommentBean> getTClass() {
        return AllDiscoverCommentBean.class;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("全部评论").setOnTitleBarListener(this);
    }

    @Override
    protected String getBackColor() {
        return "#f5f5f5";
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
