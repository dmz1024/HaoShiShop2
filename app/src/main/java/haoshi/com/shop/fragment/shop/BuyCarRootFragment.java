package haoshi.com.shop.fragment.shop;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class BuyCarRootFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {

    private BuyCarFragment fragment;

    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, fragment = new BuyCarFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_buy_car_root;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("购物车")
                .setRightContent("编辑").setOnTitleBarListener(this);
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    private boolean isEdit;

    @Override
    public void right() {
        if (fragment != null) {
            ((DefaultTitleBarView) getTitleBar()).setRightContent((isEdit = !isEdit) ? "取消编辑" : "编辑");
            fragment.choose(isEdit);
        }
    }

    @Override
    public void center() {

    }

    @OnClick(R.id.tv_pay)
    void pay() {
        if (fragment != null) {
            fragment.pay();
        }
    }
}
