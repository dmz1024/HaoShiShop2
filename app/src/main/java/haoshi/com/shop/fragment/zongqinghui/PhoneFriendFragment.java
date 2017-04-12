package haoshi.com.shop.fragment.zongqinghui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import base.fragment.NetworkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.R;
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
import static com.tencent.open.utils.Global.getPackageName;

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


//    @Override
//    protected ShowCurrentViewENUM getDefaultView() {
//        return ShowCurrentViewENUM.VIEW_IS_LOADING;
//    }

    private boolean isNoPer;

    @Override
    protected void getData() {
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
            View view = View.inflate(getContext(), R.layout.view_no_phone_per, null);
            view.findViewById(R.id.tv_set).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", "haoshi.com.shop", null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);
                        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                        localIntent.putExtra("com.android.settings.ApplicationPkgName", "haoshi.com.shop");
                    }
                    startActivity(localIntent);
                }
            });

            view.findViewById(R.id.bt_again).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    havaDataView = null;
                    getCurrentView(ShowCurrentViewENUM.VIEW_IS_LOADING);
                    getData();
                }
            });

            return view;
        }
        return super.getNoDataView();
    }

//    @Override
//    protected View getHaveDataView() {
//
//        return super.getHaveDataView();
//    }

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
