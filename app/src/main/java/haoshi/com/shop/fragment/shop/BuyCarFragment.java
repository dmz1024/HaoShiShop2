package haoshi.com.shop.fragment.shop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.BuyCarAdapter;
import haoshi.com.shop.bean.shop.BuyCarBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class BuyCarFragment extends ListNetWorkBaseFragment<BuyCarBean> {


    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new BuyCarAdapter(getContext(), (ArrayList<BuyCarBean.Data>) totalList) {
            @Override
            protected void price() {
                if (onBuyCarInterface != null) {
                    double price = 0.0;
                    boolean isChoose = true;
                    for (BuyCarBean.Data data : list) {
                        for (BuyCarBean.Data.ListBean good : data.list) {
                            if (good.isChoose) {
                                price += good.shopPrice * good.cartNum;
                            }

                        }
                        if (!data.isChoose) {
                            isChoose = false;
                        }
                    }
                    onBuyCarInterface.price(price);
                    onBuyCarInterface.isChoose(isChoose);
                }
            }
        };
    }

    public interface OnBuyCarInterface {
        void isChoose(boolean isChoose);

        void price(double price);
    }

    private OnBuyCarInterface onBuyCarInterface;

    public void setOnBuyCarInterface(OnBuyCarInterface onBuyCarInterface) {
        this.onBuyCarInterface = onBuyCarInterface;
    }

    @Override
    protected String url() {
        return ApiConstant.BUY_CAR;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<BuyCarBean> getTClass() {
        return BuyCarBean.class;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }

    public boolean edit(boolean isEdit) {
        if (mAdapter == null || totalList.size() == 0) {
            return false;
        }
        ArrayList<BuyCarBean.Data> lists = (ArrayList<BuyCarBean.Data>) this.totalList;
        for (BuyCarBean.Data data : lists) {
            data.isEdit = isEdit;
        }
        mAdapter.notifyDataSetChanged();
        return true;
    }

    public double choose(boolean isChoose) {
        if (mAdapter == null || totalList.size() == 0) {
            return -1;
        }
        double price = 0.0;

        ArrayList<BuyCarBean.Data> lists = (ArrayList<BuyCarBean.Data>) this.totalList;
        for (BuyCarBean.Data data : lists) {
            data.isChoose = isChoose;
            if (isChoose) {
                for (BuyCarBean.Data.ListBean good : data.list) {
                    price += good.shopPrice * good.cartNum;
                }
            }
            data.isHandcar = true;
        }

        mAdapter.notifyDataSetChanged();
        return price;
    }

    public void pay() {
        if (mAdapter == null || totalList.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        ArrayList<BuyCarBean.Data> totalList = (ArrayList<BuyCarBean.Data>) this.totalList;
        for (BuyCarBean.Data data : totalList) {
            for (BuyCarBean.Data.ListBean bean : data.list) {
                if (bean.isChoose) {
                    if (sb.length() > 1) {
                        sb.append(",");
                    }
                    sb.append("{\"cartId\":").append(bean.cartId).append(",").append("\"cartNum\":").append(bean.cartNum).append("}");
                }
            }
        }
        sb.append("]");
        Log.d("ddd", sb.toString());
        if (sb.length() > 2) {
            RxBus.get().post("back", "back");
            RxBus.get().post("addFragment", new AddFragmentBean(AffirmBuyFragment.getInstance(sb.toString())));
        } else {
            MyToast.showToast("请选择商品");
        }
        return;
    }
}
