package haoshi.com.shop.fragment.shop;

import android.widget.TextView;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.DrawableUtil;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/10.
 */

public class BuyCarRootFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.tv_choose)
    TextView tv_choose;
    @BindView(R.id.tv_price)
    TextView tv_price;
    private BuyCarFragment fragment;

    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, fragment = new BuyCarFragment()).commit();
        fragment.setOnBuyCarInterface(new BuyCarFragment.OnBuyCarInterface() {
            @Override
            public void isChoose(boolean isChoose) {
                tv_choose.setCompoundDrawables(DrawableUtil.setBounds(getResources().getDrawable((isChoose) ? R.mipmap.shangcheng_piont : R.mipmap.shangcheng_piont2)), null, null, null);
                tv_choose.setText(isChoose ? "全不选" : "全选");
            }

            @Override
            public void price(double price) {
                tv_price.setText(price + "");
            }
        });
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
            if (fragment.edit(!isEdit)) {
                ((DefaultTitleBarView) getTitleBar()).setRightContent((isEdit = !isEdit) ? "取消编辑" : "编辑");
            }
        }
    }

    @Override
    public void center() {

    }

    private boolean isChoose;

    @OnClick(R.id.tv_choose)
    void choose() {
        if (fragment != null) {
            double price = fragment.choose(!isChoose);
            if (price != -1) {
                tv_choose.setCompoundDrawables(DrawableUtil.setBounds(getResources().getDrawable((isChoose = !isChoose) ? R.mipmap.shangcheng_piont : R.mipmap.shangcheng_piont2)), null, null, null);
                tv_choose.setText(isChoose ? "全不选" : "全选");
                tv_price.setText(price + "");
            }
        }

    }

    @OnClick(R.id.tv_pay)
    void pay() {
        if (fragment != null) {
            fragment.pay();
        }
    }
}
