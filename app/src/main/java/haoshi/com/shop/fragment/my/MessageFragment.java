package haoshi.com.shop.fragment.my;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import haoshi.com.shop.CeshiUrl;
import haoshi.com.shop.adapter.MessageAdapter;
import haoshi.com.shop.bean.MessageBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class MessageFragment extends ListNetWorkBaseFragment<MessageBean> implements OnTitleBarListener {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MessageAdapter(getContext(), ((ArrayList<MessageBean.Data>) totalList));
    }

    @Override
    protected String url() {
        return ApiConstant.PAGEQUERY;
    }

    @Override
    protected Map<String, String> map() {
        map.put("userId", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<MessageBean> getTClass() {
        return MessageBean.class;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("通知")
                .setRightColor("#666666")
                .setRightContent("设置")
                .setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {
        RxBus.get().post("addFragment", new AddFragmentBean(new MessageSetFragment()));
    }

    @Override
    public void center() {

    }
}
