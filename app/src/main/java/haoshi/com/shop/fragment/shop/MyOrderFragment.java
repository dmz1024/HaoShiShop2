package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.bean.TipLoadingBean;
import base.fragment.ListNetWorkBaseFragment;
import base.fragment.NetworkBaseFragment;
import haoshi.com.shop.adapter.MyOrderAdapter;
import haoshi.com.shop.bean.shop.MyOrderBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class MyOrderFragment extends ListNetWorkBaseFragment<MyOrderBean> {
    private boolean isFirst;

    public static MyOrderFragment getInstance(String type, boolean isFirst, int position) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putBoolean("isFirst", isFirst);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String type;
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
            isFirst = bundle.getBoolean("isFirst");
            position = bundle.getInt("position");
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {

        return new MyOrderAdapter(getContext(), (ArrayList<MyOrderBean.Data>) totalList, position);
    }

    @Override
    protected String url() {
        return ApiConstant.WAITPAYBYPAGES;
    }

    @Override
    protected Map<String, String> map() {
        if (!TextUtils.isEmpty(type)) {
            map.put("orderStatus", type);
        }
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }


//    private Observable<Integer> orderManager;
//
//    private void initManagerOrder() {
//        if (orderManager == null) {
//            orderManager = RxBus.get().register("orderManager", Integer.class);
//            orderManager.subscribe(new Action1<Integer>() {
//                @Override
//                public void call(Integer s) {
//                    getData();
//                }
//            });
//        }
//
//    }


    @Override
    protected boolean showSucces() {
        return false;
    }


    @Override
    protected TipLoadingBean getTipLoadingBeanForListNet() {
        return new TipLoadingBean();
    }

    @Override
    protected boolean showInfo() {
        return false;
    }

    @Override
    protected Class<MyOrderBean> getTClass() {
        return MyOrderBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return isFirst;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            ((MyOrderAdapter) mAdapter).onDestroy();
        }

//        RxBus.get().unregister("orderManager",orderManager);
    }

    @Override
    protected int time() {
        return super.time();
    }
}
