package haoshi.com.shop.fragment.reg;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class PerfectRegChooseUserInfoFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    private PerfectRegInfoTagFragment fragment;

    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, fragment = new PerfectRegInfoTagFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_perfect_reg_userinfo;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("完善信息");
    }

    @OnClick(R.id.bt_choose)
    void choose() {
        if (fragment != null) {
            fragment.choose();
        }
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
