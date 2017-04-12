package haoshi.com.shop.fragment.my;

import android.view.View;


import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.OnClick;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;

/**
 * Created by dengmingzhi on 2016/12/8.
 */

public class AddressContentFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {

    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, addressFragment = new AddressFragment()).commit();
    }


    private AddressFragment addressFragment;

    @Override
    protected int getRId() {
        return R.layout.fragment_address_content;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }


    @OnClick(R.id.bt_add)
    void add() {
        RxBus.get().post("addFragment", new AddFragmentBean(new AddressEditFragment()));
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
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
