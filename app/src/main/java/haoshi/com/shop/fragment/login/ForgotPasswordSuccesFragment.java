package haoshi.com.shop.fragment.login;

import android.view.View;

import base.fragment.NotNetWorkBaseFragment;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class ForgotPasswordSuccesFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_forgot_passowrd_succes;
    }

    @Override
    protected View getTitleBarView() {
        return null;
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
}
