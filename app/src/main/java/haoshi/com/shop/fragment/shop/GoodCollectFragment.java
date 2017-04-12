package haoshi.com.shop.fragment.shop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.GoodCollectAdapter;
import haoshi.com.shop.bean.shop.GoodBean;
import haoshi.com.shop.bean.shop.GoodCollectBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
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
        return ApiConstant.MYCOLLECTIONLISTS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
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


//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!isFirst) {
//            Log.d("ddd","onResume");
//            getData();
//        }
//    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    private boolean isDelete;

    @Override
    public void right() {
        if (mAdapter != null) {
            ((GoodCollectAdapter) mAdapter).setDelete(isDelete = !isDelete);
            ((DefaultTitleBarView) getTitleBar()).setRightContent(isDelete ? "取消编辑" : "编辑");
        }
    }

    @Override
    public void center() {

    }
}
