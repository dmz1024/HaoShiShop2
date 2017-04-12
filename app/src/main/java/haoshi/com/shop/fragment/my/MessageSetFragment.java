package haoshi.com.shop.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.R;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class MessageSetFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @Override
    protected void initData() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("接收新消息", 0, 9));
        datas.add(new GeneralBean(0));
        datas.add(new GeneralBean("平台通知", 1, 9));
        datas.add(new GeneralBean("点赞", 0, 9));
        datas.add(new GeneralBean("评论", 0, 9));
        datas.add(new GeneralBean("订单发货", 1, 9));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getRId() {
        return R.layout.recycle_view;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("推送设置").setOnTitleBarListener(this);
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
