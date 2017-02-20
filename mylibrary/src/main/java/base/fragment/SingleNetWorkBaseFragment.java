package base.fragment;

import android.view.View;

import base.bean.BaseBean;

/**
 * Created by dengmingzhi on 2016/11/23.
 */

public abstract class SingleNetWorkBaseFragment<D extends BaseBean> extends NetworkBaseFragment<D> {
    @Override
    protected void manageError(boolean isWrite, D d, String msg) {
        if (!isWrite) {
            stopRefresh();
        }
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_IS_LOADING;
    }


    @Override
    protected void writeData(boolean isWrite, D bean) {
        if (!isWrite) {
            stopRefresh();
        }
    }
}
