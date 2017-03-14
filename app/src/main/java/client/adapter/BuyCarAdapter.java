package client.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.shop.BuyCarBean;
import client.bean.shop.GoodBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class BuyCarAdapter extends BaseAdapter<BuyCarBean.Data> {
    public BuyCarAdapter(Context ctx, ArrayList<BuyCarBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_buy_car, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        inintGoods(mHolder.rv_content, position);
    }

    private void inintGoods(RecyclerView rv_content, int position) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        ArrayList<GoodBean> goods = new ArrayList<>();
        goods.add(new GoodBean(false));
        goods.add(new GoodBean(true));
        GoodAdapter mAdapter = new GoodAdapter(ctx, goods);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_choose)
        ImageView iv_choose;
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
