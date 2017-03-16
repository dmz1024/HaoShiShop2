package client.fragment.shop;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import client.adapter.GoodCollectAdapter;
import client.bean.shop.GoodBean;
import client.bean.shop.GoodCollectBean;
import client.constant.ApiConstant;
import client.constant.UserInfo;
import interfaces.OnTitleBarListener;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/15.
 */

public class GoodCollectFragment extends ListNetWorkBaseFragment<GoodCollectBean> implements OnTitleBarListener {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodCollectAdapter(getContext(), (ArrayList<GoodBean>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.LISTGOODSQUERY;
    }

    @Override
    protected Map<String, String> map() {
        map.put("user_id", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<GoodCollectBean> getTClass() {
        return GoodCollectBean.class;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("商品收藏")
                .setRightContent("编辑")
                .setOnTitleBarListener(this);
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    public void left() {

    }

    private boolean isDelete;

    @Override
    public void right() {
        if (mAdapter != null) {
            ((GoodCollectAdapter) mAdapter).setDelete(isDelete = !isDelete);
        }
    }

    @Override
    public void center() {

    }
}
