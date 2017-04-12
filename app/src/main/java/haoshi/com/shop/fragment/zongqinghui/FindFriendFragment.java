package haoshi.com.shop.fragment.zongqinghui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.adapter.FindFriendAdapter;
import haoshi.com.shop.bean.zongqinghui.FindFriendBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class FindFriendFragment extends ListNetWorkBaseFragment<FindFriendBean> {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FindFriendAdapter(getContext(), (ArrayList<FindFriendBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.FIND_FAMILY;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected void writeData(boolean isWrite, FindFriendBean bean) {
        super.writeData(isWrite, bean);
        if (bean.user != null) {
            if(onUserInfoInterface!=null){
                onUserInfoInterface.user(bean.user);
            }
        }
    }


    private OnUserInfoInterface onUserInfoInterface;

    public void setOnUserInfoInterface(OnUserInfoInterface onUserInfoInterface) {
        this.onUserInfoInterface = onUserInfoInterface;
    }

    @Override
    protected Class<FindFriendBean> getTClass() {
        return FindFriendBean.class;
    }


    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
