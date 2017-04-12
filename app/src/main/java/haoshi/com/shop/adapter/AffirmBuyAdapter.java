package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.shop.AffirmBuyBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class AffirmBuyAdapter extends BaseAdapter<AffirmBuyBean.Data.ShopBean> {
    public AffirmBuyAdapter(Context ctx, ArrayList<AffirmBuyBean.Data.ShopBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_affirm_buy, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AffirmBuyBean.Data.ShopBean data = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_shop_name.setText(data.shopName);
        viewHolder.tv_shop_price.setText(data.goodsMoney + "元");
        viewHolder.tv_postage.setText(data.freight <= 0 ? "免邮" : data.freight + "元");
        initGoods(viewHolder.rv_content, data.goods);
    }

    private void initGoods(RecyclerView rv_content, ArrayList<AffirmBuyBean.Data.ShopBean.GoodsBean> goods) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        AffirmBuyGoodsAdapter mAdapter = new AffirmBuyGoodsAdapter(ctx, goods);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;
        @BindView(R.id.tv_shop_price)
        TextView tv_shop_price;
        @BindView(R.id.tv_postage)
        TextView tv_postage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

    }
}
