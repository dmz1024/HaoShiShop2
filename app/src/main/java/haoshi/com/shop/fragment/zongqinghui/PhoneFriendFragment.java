package haoshi.com.shop.fragment.zongqinghui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import base.fragment.NetworkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.ChatFriendAdapter;
import haoshi.com.shop.adapter.PhoneFriendAdapter;
import haoshi.com.shop.bean.chat.PhoneFriend;
import haoshi.com.shop.bean.chat.dao.ChatFriendBean;
import haoshi.com.shop.bean.zongqinghui.StandFriendBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.PhoneUtil;
import util.RxBus;
import view.DefaultTitleBarView;

import static base.fragment.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_DATA;
import static base.fragment.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_NETWORK;

/**
 * Created by dengmingzhi on 2017/3/6.
 * 站内好友
 */

public class PhoneFriendFragment extends ListNetWorkBaseFragment<PhoneFriend> implements OnTitleBarListener {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new PhoneFriendAdapter(getContext(), (ArrayList<PhoneFriend.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.MOBILEFRIEND;
    }

    private String mobile;

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        map.put("mobile", mobile);
        return super.map();
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    private boolean isNoPer;

    @Override
    protected void getData() {
        if (TextUtils.isEmpty(mobile)) {
            isNoPer = true;
            getCurrentView(VIEW_NO_DATA);
            return;
        }
        super.getData();

    }


    @Override
    protected View getNoDataView() {
        if (isNoPer) {
            TextView tv = new TextView(getContext());
            tv.setText("没有手机通讯录权限");
            tv.setTextColor(Color.parseColor("#999999"));
            return tv;
        }
        return super.getNoDataView();
    }

    @Override
    protected View getHaveDataView() {
        StringBuilder sb = new StringBuilder();
        ArrayList<String[]> ps = PhoneUtil.getPhone(getContext());
        for (String[] s : ps
                ) {

            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(s[1]);
        }
        mobile = sb.toString();
        return super.getHaveDataView();
    }

    @Override
    protected Class<PhoneFriend> getTClass() {
        return PhoneFriend.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("通讯录好友").setOnTitleBarListener(this);
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
