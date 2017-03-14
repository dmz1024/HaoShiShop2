package client.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.FindFlockAdapter;
import client.adapter.FindFriendAdapter;
import client.bean.zongqinghui.FindFlockBean;
import client.bean.zongqinghui.FindFriendBean;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFriendFragment extends ListNetWorkBaseFragment<FindFriendBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FindFriendAdapter(getContext(), (ArrayList<FindFriendBean.Data>) totalList);
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
    protected Class<FindFriendBean> getTClass() {
        return FindFriendBean.class;
    }


    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
