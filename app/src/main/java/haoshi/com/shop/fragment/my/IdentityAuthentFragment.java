package haoshi.com.shop.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/27.
 */

public class IdentityAuthentFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    private boolean canFirst;

    public static IdentityAuthentFragment getInstance(boolean canFirst) {
        IdentityAuthentFragment fragment = new IdentityAuthentFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("canFirst", canFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            canFirst = bundle.getBoolean("canFirst");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_identity_authentication;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("身份认证").setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }

    @OnClick(R.id.tv_geren)
    void geren() {
        RxBus.get().post("back", "back");
        RxBus.get().post("addFragment", new AddFragmentBean(GeRenAuthentFragment.getInstance(canFirst)));


    }

    @OnClick(R.id.tv_qiye)
    void qiye() {
        RxBus.get().post("back", "back");
        RxBus.get().post("addFragment", new AddFragmentBean(QiYeAuthentFragment.getInstance(canFirst)));

    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

}
