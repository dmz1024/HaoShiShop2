package haoshi.com.shop.fragment.reg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.AddFlockTypeWriteInfoAdapter;
import haoshi.com.shop.bean.reg.PerfectRegInfoTagBean;
import haoshi.com.shop.constant.ApiConstant;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class AddFlockTypeInfoWriteFragment extends ListNetWorkBaseFragment<PerfectRegInfoTagBean> {

    public static AddFlockTypeInfoWriteFragment getInstance(String tag) {
        AddFlockTypeInfoWriteFragment fragment = new AddFlockTypeInfoWriteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
    }

    private String tag;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AddFlockTypeWriteInfoAdapter(getContext(), (ArrayList<PerfectRegInfoTagBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.PERFECT_REG_INFO_TAG;
    }

    @Override
    protected Map<String, String> map() {
        map.put("tagId", tag);
        return super.map();
    }

    @Override
    protected Class<PerfectRegInfoTagBean> getTClass() {
        return PerfectRegInfoTagBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    public void choose() {
        ArrayList<PerfectRegInfoTagBean.Data> list = (ArrayList<PerfectRegInfoTagBean.Data>) this.totalList;
        boolean isOk = true;
        StringBuilder sbTag = new StringBuilder();
        exit:
        for (PerfectRegInfoTagBean.Data bean : list) {
            if (TextUtils.isEmpty(bean.chooseContent)) {
                isOk = false;
                break exit;
            } else {
                if (sbTag.length() > 1) {
                    sbTag.append(",");
                }
                sbTag.append(bean.hint).append(":").append(bean.chooseContent);

            }
        }

        if (!isOk) {
            MyToast.showToast("请完善所有信息");
            return;
        }
        RxBus.get().post("back", "back");
        RxBus.get().post("addFlockTypeRxBus", sbTag.toString());

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
