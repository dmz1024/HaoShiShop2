package client.fragment.zongqinghui;

import android.view.View;

import java.util.Map;

import base.fragment.SingleNetWorkBaseFragment;
import client.CeshiUrl;
import client.R;
import client.bean.zongqinghui.FriendInfoBean;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class FriendInfoFragment extends SingleNetWorkBaseFragment<FriendInfoBean> {
    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "single");
        return super.map();
    }

    @Override
    protected Class<FriendInfoBean> getTClass() {
        return FriendInfoBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_friend_info, null);
        return view;
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
