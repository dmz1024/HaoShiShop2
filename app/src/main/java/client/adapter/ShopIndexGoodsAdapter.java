package client.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import client.R;
import client.bean.ShopIndexBean;
import client.bean.ZanBean;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ShopIndexGoodsAdapter extends BaseAdapter<ShopIndexBean.Data> {
    public ShopIndexGoodsAdapter(Context ctx, ArrayList<ShopIndexBean.Data> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_shop_index_good, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        ShopIndexBean.Data data = list.get(position);
        Glide.with(ctx)
                .load(data.catsImg)
                .into(mHolder.iv_img);
        initGoods(mHolder.rv_content, data.goods);
    }

    private void initGoods(RecyclerView rv_content, ArrayList<ShopIndexBean.Data.GoodsBean> goods) {
        GridLayoutManager manager = new GridLayoutManager(ctx, 2);
        ShopIndexGoodAdapter mAdapter = new ShopIndexGoodAdapter(ctx, goods);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.rv_content)
        RecyclerView rv_content;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
