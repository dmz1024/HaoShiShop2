package client.fragment.index;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.ListNetWorkBaseFragment;
import client.CeshiUrl;
import client.adapter.MessageAdapter;
import client.bean.MessageBean;
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
        return CeshiUrl.SINGLE_URL;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "get_brand_info");
        map.put("brand_id", "47");
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
        RxBus.get().post("back","back");
    }

    @Override
    public void right() {
        RxBus.get().post("addFragment",new AddFragmentBean(new MessageSetFragment()));
    }

    @Override
    public void center() {

    }
}
