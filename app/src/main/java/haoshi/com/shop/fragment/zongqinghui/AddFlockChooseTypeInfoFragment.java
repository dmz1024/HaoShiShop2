package haoshi.com.shop.fragment.zongqinghui;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class AddFlockChooseTypeInfoFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    private AddFlockTypeInfoTagFragment fragment;

    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, fragment = new AddFlockTypeInfoTagFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_add_flock_choose_type_root;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("选择群类型");
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
