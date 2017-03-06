package client.fragment.index;

import android.view.View;

import base.fragment.NotNetWorkBaseFragment;
import client.R;
import interfaces.OnTitleBarListener;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public class IndexOneFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_tab, new IndexOneTabFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content, new IndexOneContentFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("宗亲会")
                .showVisiLeft(View.GONE)
                .setRightImage(R.mipmap.zqh_add)
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
