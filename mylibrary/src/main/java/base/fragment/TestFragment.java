package base.fragment;

import android.view.View;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class TestFragment extends NotNetWorkBaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_test;
    }


    @Override
    protected View getTitleBarView() {
        return null;
    }
}
