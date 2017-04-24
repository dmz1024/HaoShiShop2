package haoshi.com.shop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.LogisticsAdapter;
import haoshi.com.shop.bean.shop.LogisticsBean;
import haoshi.com.shop.constant.ApiConstant;

/**
 * Created by dengmingzhi on 2017/4/24.
 */

public class ShowLogisticsFragment extends ListNetWorkBaseFragment<LogisticsBean> {
    public static ShowLogisticsFragment getInstance(String expressId, String expressNo) {
        ShowLogisticsFragment fragment = new ShowLogisticsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", expressId);
        bundle.putString("no", expressNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;
    private String no;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        no = getArguments().getString("no");

    }

    @Override
    protected Map<String, String> map() {
        map.put("expressId", id);
        map.put("expressNo", no);
        return super.map();
    }


    @Override
    protected void writeData(boolean isWrite, LogisticsBean bean) {
        super.writeData(isWrite, bean);
        if(onLogisticsInterface!=null){
            onLogisticsInterface.info(bean.info);
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new LogisticsAdapter(getContext(), (ArrayList<LogisticsBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.VIEWLOGISTICS;
    }

    @Override
    protected Class<LogisticsBean> getTClass() {
        return LogisticsBean.class;
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    protected View getTitleBarView() {
        return null;
    }

    private OnLogisticsInterface onLogisticsInterface;

    public void setOnLogisticsInterface(OnLogisticsInterface onLogisticsInterface) {
        this.onLogisticsInterface = onLogisticsInterface;
    }

    public interface OnLogisticsInterface{
        void info(LogisticsBean.Info info);
    }
}
