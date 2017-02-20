package base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.adapter.AreaAdapter;
import base.bean.ChooseAreaBean;
import base.bean.ListBaseBean;
import base.bean.SerializableMap;
import interfaces.OnChooseAreaIntenface;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/2/13.
 */

public class BaseAreaFragment extends ListNetWorkBaseFragment<ChooseAreaBean> {
    private String url;
    private SerializableMap maps;

    public static BaseAreaFragment getInstance(String url, SerializableMap map) {
        BaseAreaFragment fragment = new BaseAreaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putSerializable("map", map);
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    protected void writeData(boolean isWrite, ChooseAreaBean bean) {
        if(bean.data.size()==0){
            RxBus.get().post("area_no",false);
            return;
        }
        super.writeData(isWrite, bean);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            url=bundle.getString("url");
            maps= (SerializableMap) bundle.getSerializable("map");
        }

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AreaAdapter(getContext(), (ArrayList<ChooseAreaBean.Data>) totalList);
    }


    @Override
    protected String url() {
        return url;
    }

    @Override
    protected Map<String, String> map() {
        return maps.getMap();
    }

    @Override
    protected Class<ChooseAreaBean> getTClass() {
        return ChooseAreaBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
