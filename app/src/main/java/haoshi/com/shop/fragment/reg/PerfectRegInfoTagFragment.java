package haoshi.com.shop.fragment.reg;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.PerfectRegInfoTagAdapter;
import haoshi.com.shop.bean.reg.PerfectRegInfoTagBean;
import haoshi.com.shop.constant.ApiConstant;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class PerfectRegInfoTagFragment extends ListNetWorkBaseFragment<PerfectRegInfoTagBean> {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new PerfectRegInfoTagAdapter(getContext(), (ArrayList<PerfectRegInfoTagBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.PERFECT_REG_INFO_TAG;
    }

    @Override
    protected Class<PerfectRegInfoTagBean> getTClass() {
        return PerfectRegInfoTagBean.class;
    }


    @Override
    protected LinearLayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3);
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    public void choose() {
        if (mAdapter == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList<PerfectRegInfoTagBean.Data> data = (ArrayList<PerfectRegInfoTagBean.Data>) totalList;
        for (PerfectRegInfoTagBean.Data bean : data) {
            if (bean.isChoose) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(bean.tid);
            }
        }
        if (sb.toString().split(",").length < 3) {
            MyToast.showToast("请至少选择三项");
            return;
        }
        RxBus.get().post("back", "back");
        RxBus.get().post("addFragment", new AddFragmentBean(PerfectRegWriteInfoRootFragment.getInstance(sb.toString())));
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
