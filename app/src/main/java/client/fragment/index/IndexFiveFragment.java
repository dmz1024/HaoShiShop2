package client.fragment.index;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recker.flyshapeimageview.ShapeImageView;

import java.util.ArrayList;

import api.TestConstant;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;
import client.pop.PopRenZTip;
import client.R;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/1/16.
 */

public class IndexFiveFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.rv_item)
    RecyclerView rv_item;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_head)
    ShapeImageView iv_head;
    @BindView(R.id.iv_set)
    ImageView iv_set;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_check)
    TextView tv_check;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_ping)
    TextView tv_ping;

    @Override
    protected void initData() {
        initOrder();
        initItem();
        initInfo();
    }


    @Override
    protected int getRId() {
        return R.layout.fragment_five;
    }


    @Override
    protected View getTitleBarView() {
        return null;
    }

    private void initInfo() {
        Glide.with(getContext()).load(TestConstant.IMAGE).into(iv_bg);
        Glide.with(getContext()).load(TestConstant.IMAGE).into(iv_head);
    }

    private void initOrder() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("待付款", R.mipmap.wode_daifukuan, null, 2, 1));
        datas.add(new GeneralBean("待发货", R.mipmap.wode_fahuo, null, 0, 1));
        datas.add(new GeneralBean("已发货", R.mipmap.wode_yi, null, 0, 1));
        datas.add(new GeneralBean("待评价", R.mipmap.wode_pingjia, null, 2, 1));
        datas.add(new GeneralBean("退款", R.mipmap.wode_tui, null, 2, 1));
        GridLayoutManager manager = new GridLayoutManager(getContext(), 5) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GeneralAdapter mAdatpter = new GeneralAdapter(getContext(), datas);
        rv_order.setAdapter(mAdatpter);
        rv_order.setLayoutManager(manager);
    }

    private void initItem() {
        ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("我已发布", R.mipmap.wode_roll, null, 2));
        datas.add(new GeneralBean("我的收藏", R.mipmap.wode_xihuan, null, 2));
        datas.add(new GeneralBean("我的打赏", R.mipmap.wode_jilu, null, 2));
        datas.add(new GeneralBean("申请建群", R.mipmap.wode_jiaqun, null, 2));
        datas.add(new GeneralBean("用户协议", R.mipmap.wode_xieyi, null, 2));
        datas.add(new GeneralBean("联系我们", R.mipmap.wode_lianxi, null, 2));
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GeneralAdapter mAdatpter = new GeneralAdapter(getContext(), datas);
        rv_item.setAdapter(mAdatpter);
        rv_item.setLayoutManager(manager);
    }


    @OnClick(R.id.iv_set)
    void set() {
        RxBus.get().post("addFragment", new AddFragmentBean(new SetFragment()));
    }

    @OnClick(R.id.tv_message)
    void message() {
        RxBus.get().post("addFragment", new AddFragmentBean(new MessageFragment()));
    }

    @OnClick(R.id.tv_zan)
    void zan() {
        RxBus.get().post("addFragment", new AddFragmentBean(new ZanFragment()));
    }
    @OnClick(R.id.tv_ping)
    void ping() {
        RxBus.get().post("addFragment", new AddFragmentBean(new CommentFragment()));
    }

    @OnClick(R.id.tv_check)
    void check() {
        new PopRenZTip(getContext()).showAtLocation(false);
    }

}
