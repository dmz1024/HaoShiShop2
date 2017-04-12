package haoshi.com.shop.fragment.my;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.MyGiveAdapter;
import haoshi.com.shop.bean.my.MyGiveBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class MyGiveFragment extends ListNetWorkBaseFragment<MyGiveBean> implements OnTitleBarListener {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyGiveAdapter(getContext(), (ArrayList<MyGiveBean.Data>) totalList);
    }

    @Override
    protected String url() {
        return ApiConstant.MYREWARDS;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<MyGiveBean> getTClass() {
        return MyGiveBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("我的打赏")
                .setOnTitleBarListener(this);
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
