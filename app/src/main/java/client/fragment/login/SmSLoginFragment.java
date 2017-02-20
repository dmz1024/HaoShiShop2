package client.fragment.login;

import base.fragment.NotNetWorkBaseFragment;
import client.R;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class SmSLoginFragment extends NotNetWorkBaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_sms_login;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("登录");
    }
}
