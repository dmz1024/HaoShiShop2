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

public class SetFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @Override
    protected void initData() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("个人设置", new PeosonSetFragment(), 4));
        datas.add(new GeneralBean("推送设置", new MessageSetFragment(), 4));
        datas.add(new GeneralBean("清理缓存", null, 5, "20M"));
        datas.add(new GeneralBean("关于我们", new AboutUsFragment(), 4));
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
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("设置").setOnTitleBarListener(this);
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
