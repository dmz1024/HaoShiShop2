package client.fragment.index;

import android.view.View;

import base.fragment.NotNetWorkBaseFragment;
import client.R;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public class IndexFragment extends NotNetWorkBaseFragment {
    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_bottom,new IndexBottomFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content,new IndexContentFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_index;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }
}
