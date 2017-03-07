package client.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import api.TestConstant;
import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import client.R;
import client.bean.ShopIndexBean;
import client.bean.ZanBean;
import client.fragment.shop.GoodDescFragment;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ShopIndexGoodAdapter extends BaseAdapter<ShopIndexBean.Data.GoodsBean> {
    public ShopIndexGoodAdapter(Context ctx, ArrayList<ShopIndexBean.Data.GoodsBean> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_shop_index_good_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        ShopIndexBean.Data.GoodsBean data = list.get(position);
        mHolder.tv_name.setText(data.goodsName);
        Glide.with(ctx).load(data.goodsImg).into(mHolder.iv_img);
        mHolder.tv_price.setText(data.shopPrice);
        mHolder.tv_old_price.setText("￥" + data.marketPrice);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_old_price)
        TextView tv_old_price;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment", new AddFragmentBean(GoodDescFragment.getInstance(list.get(layoutPosition).goodsId)));
        }
    }
}
