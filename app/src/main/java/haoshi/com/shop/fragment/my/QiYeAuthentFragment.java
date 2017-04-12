package haoshi.com.shop.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Map;

import base.bean.SingleBaseBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/27.
 */

public class QiYeAuthentFragment extends SingleNetWorkBaseFragment<SingleBaseBean> implements OnTitleBarListener {

    private boolean canFirst;

    public static QiYeAuthentFragment getInstance(boolean canFirst) {
        QiYeAuthentFragment fragment = new QiYeAuthentFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("canFirst", canFirst);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            canFirst = bundle.getBoolean("canFirst");
        }
    }


    @Override
    protected String url() {
        return ApiConstant.SHOPDISPLAYS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token",UserInfo.token);
        return super.map();
    }
    @Override
    protected Class<SingleBaseBean> getTClass() {
        return SingleBaseBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_qiye_authent, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("企业认证")
                .setOnTitleBarListener(this);
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return canFirst ? ShowCurrentViewENUM.VIEW_IS_LOADING : ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    public void center() {

    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return canFirst;
    }
}
