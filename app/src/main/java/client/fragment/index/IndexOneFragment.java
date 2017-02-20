package client.fragment.index;

import android.view.View;

import base.fragment.NotNetWorkBaseFragment;
import client.R;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public class IndexOneFragment extends NotNetWorkBaseFragment {
    @Override
    protected void initData() {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_tab,new IndexOneTabFragment()).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fg_content,new IndexOneContentFragment()).commit();
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_one;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }
}
